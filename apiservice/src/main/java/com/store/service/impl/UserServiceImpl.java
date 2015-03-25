package com.store.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.store.dto.AddorUpdateUserDTO;
import com.store.dto.BatchRequestAccessDTO;
import com.store.dto.ReportUsageDTO;
import com.store.dto.VerifyVpnAccessDTO;
import com.store.redis.dao.BlockUserDAO;
import com.store.redis.dao.DeviceKeyTakenDAO;
import com.store.redis.dao.ServerRedisDAO;
import com.store.redis.dao.UserRedisDAO;
import com.store.redis.model.SessionUsage;
import com.store.redis.model.VpnUser;
import com.store.result.BatchRequestAccessResult;
import com.store.result.LoginResult;
import com.store.result.StatusResult;
import com.store.service.UserService;
import com.store.utils.BillingCycleHelper;
import com.store.utils.Constants;
import com.store.utils.SHA256Generator;

@Component("userService")
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);
	
	@Autowired
	private ServerRedisDAO serverRedisDAO;
	
	@Autowired
	private UserRedisDAO userRedisDAO;
	
	@Autowired
	private BlockUserDAO blockUserDAO;
	
	@Autowired
	private DeviceKeyTakenDAO deviceKeyTakenDAO;

	/** 
	 * steps:
	 * 
	 * 1. validate user existence, user status(active/deactive), email/password
	 * 2. validate product/incoming IP by product key
	 * 3. (for usage-sensitive product user) validate if usage is already more than limit
	 * 	  rule: if currentTimeStamp < currentCycleEndTimestamp,
	 * 				then determine validity by if usage already passes the upper-bound limit
	 * 			else 
	 * 				then currentCycleEndTimestamp += period
	 * 				then usage = 0
	 * 				then valid
	 */
	public StatusResult handleStartUseVpnService(VerifyVpnAccessDTO dto) {
		LoginResult result = new LoginResult(Constants.SUCCESS);
		
		// step2
		// get product key by incoming IP
		String productKey = serverRedisDAO.findProductKeyServerByIp(dto
				.getIncomingIp());
		if (productKey == null) {
			if(logger.isErrorEnabled()) {
				logger.error("Unknown ip: " + dto.getIncomingIp());
			}
			result.setStatus(Constants.IP_UNKNOWN);
			return result;
		}

		// validate user/vpn server mapping
		VpnUser user = userRedisDAO.findUserByKey(dto.getEmail(), productKey);
		if (user == null) {
			if(logger.isErrorEnabled()) {
				logger.error("Unknown email: " + dto.getEmail());
			}
			result.setStatus(Constants.EMAIL_INCORRECT);
			return result;
		}

		// step1
		// validate user status
		if (false == user.getStatus()) {
			if(logger.isErrorEnabled()) {
				logger.error("user deactivated: " + dto.getEmail());
			}
			result.setStatus(Constants.USER_DEACTIVE);
			return result;
		}

		// validate password
		String hashedPassword;
		try {
			hashedPassword = SHA256Generator.hash(dto.getPassword()
					+ user.getSalt());
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			result.setStatus(Constants.GENERAL_FAILURE);
			return result;
		}

		if (hashedPassword == null
				|| !hashedPassword.equalsIgnoreCase(user.getPassword())) {
			if(logger.isErrorEnabled()) {
				logger.error("Invalid password: " + dto.getEmail() + " | " + dto.getPassword());
			}
			result.setStatus(Constants.LOGIN_FAILURE);
			return result;
		}

		// step3
		// validate user bandwidth usage if product is usage sensitive
		Long current = System.currentTimeMillis();
		if (BillingCycleHelper.bANewBillingCycleShouldStart(
				user.getCurrentCycleEndTimestamp(), current)) {//start a new cycle
			Long nextBillingTimestamp = BillingCycleHelper
					.getNextBillingTimestamp(user.getServiceStartTimestamp(),
							user.getPeriod(), current);
			user.setCurrentCycleEndTimestamp(nextBillingTimestamp);
			resetUsage(user);//reset usage for new billing cycle
			userRedisDAO.saveOrUpdateUser(user);
		} else {//still in old cycle
			if (user.getTotalUsageofAllSessions() > user.getUserUsageLimit()) {
				if(logger.isErrorEnabled()) {
					logger.error("User: " + dto.getEmail() + " reached usage limit.");
				}
				result.setStatus(Constants.REACH_USAGE_LIMIT);
				return result;
			}
		}

		return result;
	}
	
	/** 
	 * steps:
	 * 
	 * 0. for free-trail account, validate if the device is already used by another account, if yes, block the usage
	 * 2. validate product/incoming IP by product key
	 */
	public StatusResult handleControlDeviceService(VerifyVpnAccessDTO dto) {
		LoginResult result = new LoginResult(Constants.SUCCESS);
		
		// step2
		// get product key by incoming IP
		String productKey = serverRedisDAO.findProductKeyServerByIp(dto
				.getIncomingIp());
		if (productKey == null) {
			if(logger.isErrorEnabled()) {
				logger.error("Unknown ip: " + dto.getIncomingIp());
			}
			result.setStatus(Constants.IP_UNKNOWN);
			return result;
		}
		
		//step 0
		if(productKey.equalsIgnoreCase(Constants.PRODUCT.FREETRIAL.getProductKey()) && 
				deviceKeyTakenDAO.blockDevice(dto.getDeviceKey(), dto.getEmail())) {
			//disable the new account
			VpnUser _vpnUser = userRedisDAO.findUserByKey(dto.getEmail(), productKey);
			if(_vpnUser == null) {
				result.setStatus(Constants.USER_MISSING);
				return result;
			} else {
				_vpnUser.setStatus(false);
				userRedisDAO.saveOrUpdateUser(_vpnUser);
			}
			result.setStatus(Constants.DEVICE_ALREADY_TAKEN);
			return result;
		}

		return result;//return default
	}
	
	/**
	 * steps:
	 * 
	 * 1. validate email and product key existence
	 * 	  if not, return errors
	 * 2. update usage data to redis
	 * 	  rule: if old cycle (currentTimeStamp < currentCycleEndTimestamp)
	 * 				then aggregate usage within the current cycle
	 * 			else for new cycle
	 * 				then currentCycleEndTimestamp += period
	 * 				resetUsage(user);//reset usage for new billing cycle
	 * 3. update block user list
	 */
	public StatusResult handleReportVpnUsageService(ReportUsageDTO dto) {
	
		LoginResult result = new LoginResult(Constants.SUCCESS);

		// step1
		// get product key by incoming IP
		String productKey = serverRedisDAO.findProductKeyServerByIp(dto
				.getVpnServerIp());
		if (productKey == null) {
			if(logger.isErrorEnabled()) {
				logger.error("Unknown ip: " + dto.getVpnServerIp());
			}
			result.setStatus(Constants.IP_UNKNOWN);
			return result;
		}

		// validate user/server mapping
		VpnUser user = userRedisDAO.findUserByKey(dto.getEmail(), productKey);
		if (user == null) {
			if(logger.isErrorEnabled()) {
				logger.error("Unknown email: " + dto.getEmail());
			}
			result.setStatus(Constants.EMAIL_INCORRECT);
			return result;
		}
		
		// step2 validate/update usage
		Long current = System.currentTimeMillis();
		if (BillingCycleHelper.bANewBillingCycleShouldStart(
				user.getCurrentCycleEndTimestamp(), current)) {// start a new
																// cycle
			Long nextBillingTimestamp = BillingCycleHelper
					.getNextBillingTimestamp(user.getServiceStartTimestamp(),
							user.getPeriod(), current);
			user.setCurrentCycleEndTimestamp(nextBillingTimestamp);
			resetUsage(user);// reset usage for new billing cycle
			userRedisDAO.saveOrUpdateUser(user);
		} else {// still in old cycle
			Long thisUsage = dto.getDownUsage() > dto.getUpUsage() ? dto
					.getDownUsage() : dto.getUpUsage();// extract usage
			aggregateUsage(user, dto.getSessionId(), thisUsage);
			userRedisDAO.saveOrUpdateUser(user);
		}

		// 3. update block user list
		if(logger.isErrorEnabled()) {
			logger.error("###about to update block list");
		}
		if (user.getTotalUsageofAllSessions() > user.getUserUsageLimit()) {
			
			if(logger.isErrorEnabled()) {
				logger.error("###update block list");
			}
			blockUserDAO.addBlockUser(dto.getEmail(), productKey,
					user.getCurrentCycleEndTimestamp());
		}

		return result;
	}
	
	/**
	 * steps:
	 * 1. verify block user list
	 * 2. remove invalid entries
	 */
	public BatchRequestAccessResult handleBatchRequestAccessService(BatchRequestAccessDTO dto) {
		BatchRequestAccessResult result = new BatchRequestAccessResult(Constants.SUCCESS);
		List<String> blockList = new ArrayList<String>();
		result.setBlockList(blockList);
		
		String productKey = serverRedisDAO.findProductKeyServerByIp(dto
				.getVpnServerIp());
		if (productKey == null) {
			if(logger.isErrorEnabled()) {
				logger.error("Unknown ip: " + dto.getVpnServerIp());
			}
			result.setStatus(Constants.IP_UNKNOWN);
			return result;
		}
		
		for(String email : dto.getEmails()) {
			if(blockUserDAO.verifyBlockUser(email, productKey)) {
				blockList.add(email);
			}
		}
		return result;
	}
	
	public void handleAddUserService(AddorUpdateUserDTO dto) {
		Long currentTimeStamp = System.currentTimeMillis();
		VpnUser user = new VpnUser();
		user.setEmail(dto.getEmail());
		
		String hashedPassword;
		try {
			hashedPassword = SHA256Generator.hash(dto.getPassword()
						+ dto.getSalt());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		user.setSalt(dto.getSalt());
		user.setPassword(hashedPassword);
		user.setStatus(dto.getStatus());
		user.setProductKey(dto.getProductKey());
		
		
		user.setServiceStartTimestamp(currentTimeStamp);
		user.setPeriod(dto.getPeriod());
		user.setCurrentCycleEndTimestamp(BillingCycleHelper
				.getNextBillingTimestamp(dto.getServiceStartTimestamp(),
						dto.getPeriod(), currentTimeStamp + 1));//make sure next billing time is after start time

		user.setUserUsageLimit(Constants.FREE_TRIAL_USAGE_LIIMIT);//default user usage limit
		user.setTotalUsageofAllSessions(0L);
		user.setTotalUsageofExpiredSessions(0L);
		user.setSessionUsageMap(null);
		userRedisDAO.saveOrUpdateUser(user);
	}
	
	public void handleUpdateUserService(AddorUpdateUserDTO dto) {
		VpnUser user = new VpnUser();
		user.setEmail(dto.getEmail());
		
		String hashedPassword;
		try {
			hashedPassword = SHA256Generator.hash(dto.getPassword()
						+ dto.getSalt());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		user.setSalt(dto.getSalt());
		user.setPassword(hashedPassword);
		
		if(dto.getStatus() != null) {
			user.setStatus(dto.getStatus());
		}
		if(dto.getProductKey() != null) {
			user.setProductKey(dto.getProductKey());
		}
		if(dto.getServiceStartTimestamp() != null) {
			user.setServiceStartTimestamp(dto.getServiceStartTimestamp());
		}
		if(dto.getPeriod() != null) {
			user.setPeriod(dto.getPeriod());	
		}
		if(dto.getCurrentCycleEndTimestamp() != null) {
			user.setCurrentCycleEndTimestamp(dto.getCurrentCycleEndTimestamp());
		}
		if(dto.getUserUsageLimit() != null) {
			user.setUserUsageLimit(dto.getUserUsageLimit());
		}
		if(dto.getTotalUsageofAllSessions() != null) {
			user.setTotalUsageofAllSessions(dto.getTotalUsageofAllSessions());
		}
		if(dto.getTotalUsageofExpiredSessions() != null) {
			user.setTotalUsageofExpiredSessions(dto.getTotalUsageofExpiredSessions());
		}
		if(dto.getSessionUsageMap() != null) {
			user.setSessionUsageMap(dto.getSessionUsageMap());
		}
		userRedisDAO.saveOrUpdateUser(user);
	}
	
	public void handleDeleteUserService(String productKey, String email) {
		VpnUser user = new VpnUser();
		user.setEmail(email);
		user.setProductKey(productKey);
		userRedisDAO.deleteUser(user);
	}
	
	private void resetUsage(VpnUser user) {
		user.setUserUsageLimit(Constants.FREE_TRIAL_USAGE_LIIMIT);//reset to default, promotion may increase this value
		user.setTotalUsageofAllSessions(0L);
		user.setTotalUsageofExpiredSessions(0L);
		user.setSessionUsageMap(null);
	}
	
	private void aggregateUsage(VpnUser user, Long sessionId, Long usage) {
		if(user == null) {
			if(logger.isErrorEnabled()) {
				logger.error("aggregateUsage null user");
			}
			return;
		}
		if(sessionId == null) {
			if(logger.isErrorEnabled()) {
				logger.error("aggregateUsage null sessionId");
			}
			return;
		}
		if(usage == null) {
			if(logger.isErrorEnabled()) {
				logger.error("aggregateUsage null usage");
			}
			return;
		}
		
		//add for map
		Map<Long, SessionUsage> sessionMap = user.getSessionUsageMap();
		if(sessionMap == null) {
			sessionMap = new HashMap<Long, SessionUsage>();
			user.setSessionUsageMap(sessionMap);
		}
		
		SessionUsage sessionUsage = sessionMap.get(sessionId);
		if(sessionUsage == null) {//new session
			sessionUsage = new SessionUsage();
			sessionUsage.setUsage(usage);
			sessionMap.put(sessionId, sessionUsage);
		} else {//existing session
			sessionUsage.setUsage(usage);
		}
		sessionUsage.setLastModifyTimestamp(System.currentTimeMillis());
		
		//clear expired sessions & add for totals
		Long unExpiredUsage = 0L;
		for (Iterator<Map.Entry<Long, SessionUsage>> it = sessionMap.entrySet()
				.iterator(); it.hasNext();) {
			Map.Entry<Long, SessionUsage> entry = it.next();
			if (sessionExpired(entry.getValue())) {// a session expired
				user.setTotalUsageofExpiredSessions(user
						.getTotalUsageofExpiredSessions()
						+ entry.getValue().getUsage());
				it.remove();
			} else {
				unExpiredUsage += entry.getValue().getUsage();
			}
		}
		user.setTotalUsageofAllSessions(user.getTotalUsageofExpiredSessions()
				+ unExpiredUsage);
	}
	
	private Boolean sessionExpired(SessionUsage sessionUsage) {
		if (sessionUsage == null) {
			throw new RuntimeException("sessionExpired got null sessionUsage");
		}

		if (sessionUsage.getLastModifyTimestamp() == null) {
			return false;
		}

		return System.currentTimeMillis()
				- sessionUsage.getLastModifyTimestamp() > Constants.DEFAULT_SESSION_TIMEOUT ? true
				: false;
	}
}
