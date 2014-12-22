package com.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.store.dto.AddorUpdateUserDTO;
//import org.springframework.transaction.annotation.Transactional;
import com.store.dto.ReportUsageDTO;
import com.store.dto.VerifyVpnAccessDTO;
import com.store.redis.client.RedisClient;
import com.store.redis.client.VpnUser;
import com.store.result.LoginResult;
import com.store.result.StatusResult;
import com.store.service.UserService;
import com.store.utils.BillingCycleHelper;
import com.store.utils.Constants;
import com.store.utils.SHA256Generator;

@Component("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private RedisClient redisClient;

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
		String productKey = redisClient.findProductKeyServerByIp(dto
				.getIncomingIp());
		if (productKey == null) {
			result.setStatus(Constants.IP_UNKNOWN);
			return result;
		}

		// validate user/vpn server mapping
		VpnUser user = redisClient.findUserByKey(dto.getEmail(), productKey);
		if (user == null) {
			result.setStatus(Constants.EMAIL_INCORRECT);
			return result;
		}

		// step1
		// validate user status
		if (false == user.getStatus()) {
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
			user.setUsage(0L);
			redisClient.saveOrUpdateUser(user);
		} else {//still in old cycle
			if (user.getUsage() > Constants.FREE_TRIAL_USAGE_LIIMIT) {
				result.setStatus(Constants.REACH_USAGE_LIMIT);
				return result;
			}
		}

		return result;
	}
	
	/**
	 * steps:
	 * 
	 * 1. validate email and product key existence
	 * 	  if not, return errors
	 * 2. update usage data to redis
	 * 	  rule: if currentTimeStamp < currentCycleEndTimestamp,
	 * 				then aggregate usage within the current cycle
	 * 				then determine validity by if usage already passes the upper-bound limit
	 * 			else 
	 * 				then currentCycleEndTimestamp += period
	 * 				then usage = (0 + reported usage)
	 * 				then determine validity by if usage already passes the upper-bound limit
	 */
	public StatusResult handleReportVpnUsageService(ReportUsageDTO dto) {
	
		LoginResult result = new LoginResult(Constants.SUCCESS);

		// step1
		// get product key by incoming IP
		String productKey = redisClient.findProductKeyServerByIp(dto
				.getVpnServerIp());
		if (productKey == null) {
			result.setStatus(Constants.IP_UNKNOWN);
			return result;
		}

		// validate user/vpn server mapping
		VpnUser user = redisClient.findUserByKey(dto.getEmail(), productKey);
		if (user == null) {
			result.setStatus(Constants.EMAIL_INCORRECT);
			return result;
		}
		
		// validate/update usage
		Long thisUsage = dto.getDownUsage()>dto.getUpUsage() ? dto.getDownUsage():dto.getUpUsage();
		
		Long current = System.currentTimeMillis();
		if (BillingCycleHelper.bANewBillingCycleShouldStart(
				user.getCurrentCycleEndTimestamp(), current)) {//start a new cycle
			Long nextBillingTimestamp = BillingCycleHelper
					.getNextBillingTimestamp(user.getServiceStartTimestamp(),
							user.getPeriod(), current);
			user.setCurrentCycleEndTimestamp(nextBillingTimestamp);
			user.setUsage(thisUsage);
			redisClient.saveOrUpdateUser(user);
		} else {//still in old cycle
			user.setUsage(user.getUsage()+thisUsage);
			redisClient.saveOrUpdateUser(user);
			if (user.getUsage() > Constants.FREE_TRIAL_USAGE_LIIMIT) {
				result.setStatus(Constants.REACH_USAGE_LIMIT);
				return result;
			}
		}

		return result;
	}
	
	public StatusResult handleAddUserService(AddorUpdateUserDTO dto) {
		
	}
	
	public StatusResult handleUpdateUserService(AddorUpdateUserDTO dto) {
		
	}
}
