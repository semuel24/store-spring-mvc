package com.store.db.dao.iml.mysql;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.store.db.dao.SessionUsageDAO;
import com.store.db.dao.UserUsageDAO;
import com.store.entity.ApiSessionUsage;
import com.store.entity.ApiUserUsage;
import com.store.exception.DBException;
import com.store.redis.model.SessionUsage;
import com.store.redis.model.VpnUser;

@Transactional
@Component
public class UserUsageDAOImpl extends StoreDAOImpl<ApiUserUsage> implements
		UserUsageDAO {
	
	@Autowired
	private SessionUsageDAO sessionUsageDAO;

	public void createUser(VpnUser redisUser) {
		ApiUserUsage userUsage = new ApiUserUsage();
		if (redisUser.getEmail() != null) {
			userUsage.setEmail(redisUser.getEmail());
		}
		if (redisUser.getPassword() != null) {
			userUsage.setPassword(redisUser.getPassword());
		}
		if (redisUser.getSalt() != null) {
			userUsage.setSalt(redisUser.getSalt());
		}
		if (redisUser.getStatus() != null) {
			userUsage.setStatus(redisUser.getStatus());
		}
		if (redisUser.getProductKey() != null) {
			userUsage.setProductKey(redisUser.getProductKey());
		}
		if (redisUser.getServiceStartTimestamp() != null) {
			userUsage.setServiceStartTimestamp(redisUser
					.getServiceStartTimestamp());
		}
		if (redisUser.getPeriod() != null) {
			userUsage.setPeriod(redisUser.getPeriod());
		}
		if (redisUser.getCurrentCycleEndTimestamp() != null) {
			userUsage.setCurrentCycleEndTimestamp(redisUser
					.getCurrentCycleEndTimestamp());
		}
		if (redisUser.getUserUsageLimit() != null) {
			userUsage.setUserUsageLimit(redisUser.getUserUsageLimit());
		}
		if (redisUser.getTotalUsageofExpiredSessions() != null) {
			userUsage.setTotalUsageofExpiredSessions(redisUser
					.getTotalUsageofExpiredSessions());
		}
		if (redisUser.getTotalUsageofAllSessions() != null) {
			userUsage.setTotalUsageofAllSessions(redisUser
					.getTotalUsageofAllSessions());
		}
		userUsage.setCreatetime(new Date());

		create(userUsage);
	}
	
	public void updateUser(VpnUser redisUser) throws DBException {
		ApiUserUsage apiUserUsage = findApiUserUsage(redisUser.getEmail(),
				redisUser.getProductKey());
		if (apiUserUsage == null) {
			throw new RuntimeException("Trying to update an non-existing user");
		}
		// update whatever passed-in
		apiUserUsage.setEmail(redisUser.getEmail());
		apiUserUsage.setPassword(redisUser.getPassword());
		apiUserUsage.setSalt(redisUser.getSalt());
		apiUserUsage.setStatus(redisUser.getStatus());
		apiUserUsage.setProductKey(redisUser.getProductKey());
		apiUserUsage.setServiceStartTimestamp(redisUser
				.getServiceStartTimestamp());
		apiUserUsage.setPeriod(redisUser.getPeriod());
		apiUserUsage.setCurrentCycleEndTimestamp(redisUser
				.getCurrentCycleEndTimestamp());
		apiUserUsage.setUserUsageLimit(redisUser.getUserUsageLimit());
		apiUserUsage.setTotalUsageofExpiredSessions(redisUser
				.getTotalUsageofExpiredSessions());
		apiUserUsage.setTotalUsageofAllSessions(redisUser
				.getTotalUsageofAllSessions());
		this.update(apiUserUsage);
		
		/**
		 * update sessions if needed
		 * Convention: 
		 * if Map<Long, SessionUsage> sessionUsageMap is null, do nothing to sessions
		 * else if Map<Long, SessionUsage> sessionUsageMap is an empty Map, clear all existing sessions
		 */
		if (redisUser.getSessionUsageMap() == null) {
			return;
		} else if (redisUser.getSessionUsageMap().isEmpty()) {
			List<ApiSessionUsage> _list = sessionUsageDAO
					.findUsageSessions(apiUserUsage.getId());
			for (ApiSessionUsage _session : _list) {
				sessionUsageDAO.delete(_session);// to be improved!!!
			}
		} else {
			for (Long _sessionId : redisUser.getSessionUsageMap().keySet()) {
				SessionUsage _sessionUsage = redisUser.getSessionUsageMap()
						.get(_sessionId);
				ApiSessionUsage _apiSessionUsage = sessionUsageDAO
						.findSessionBySessionId(_sessionId);
				boolean toCreate = false;
				if (_apiSessionUsage == null) {
					toCreate = true;
					_apiSessionUsage = new ApiSessionUsage();
				}
				_apiSessionUsage.setLastModifyTimestamp(_sessionUsage
						.getLastModifyTimestamp());
				_apiSessionUsage.setSessionId(_sessionId);
				_apiSessionUsage.setUsage(_sessionUsage.getUsage());
				_apiSessionUsage.setUserUsageId(apiUserUsage.getId());
				if (toCreate) {
					sessionUsageDAO.create(_apiSessionUsage);
				} else {
					sessionUsageDAO.update(_apiSessionUsage);
				}
			}
		}
	}

	/**
	 * only needs email and productKey
	 */
	public void deleteUser(VpnUser redisUser) {
		throw new UnsupportedOperationException("UserRedisDAOImpl.deleteUser()");
	}

	public VpnUser findUserByKey(String email, String productKey) {
		VpnUser vpnServer = new VpnUser();
		ApiUserUsage apiUserUsage = findApiUserUsage(email, productKey);
		if (apiUserUsage == null) {
			return null;
		}
		// transform
		vpnServer.setEmail(email);
		vpnServer.setProductKey(productKey);
		if (apiUserUsage.getPassword() != null) {
			vpnServer.setPassword(apiUserUsage.getPassword());
		}
		if (apiUserUsage.getSalt() != null) {
			vpnServer.setSalt(apiUserUsage.getSalt());
		}
		if (apiUserUsage.getStatus() != null) {
			vpnServer.setStatus(apiUserUsage.getStatus());
		}
		if (apiUserUsage.getServiceStartTimestamp() != null) {
			vpnServer.setServiceStartTimestamp(apiUserUsage
					.getServiceStartTimestamp());
		}
		if (apiUserUsage.getPeriod() != null) {
			vpnServer.setPeriod(apiUserUsage.getPeriod());
		}
		if (apiUserUsage.getCurrentCycleEndTimestamp() != null) {
			vpnServer.setCurrentCycleEndTimestamp(apiUserUsage
					.getCurrentCycleEndTimestamp());
		}
		if (apiUserUsage.getUserUsageLimit() != null) {
			vpnServer.setUserUsageLimit(apiUserUsage.getUserUsageLimit());
		}
		if (apiUserUsage.getTotalUsageofExpiredSessions() != null) {
			vpnServer.setTotalUsageofExpiredSessions(apiUserUsage
					.getTotalUsageofExpiredSessions());
		}
		if (apiUserUsage.getTotalUsageofAllSessions() != null) {
			vpnServer.setTotalUsageofAllSessions(apiUserUsage
					.getTotalUsageofAllSessions());
		}

		List<ApiSessionUsage> sessions = sessionUsageDAO.findUsageSessions(apiUserUsage.getId());
		Map<Long, SessionUsage> sessionMap = new HashMap<Long, SessionUsage>();
		vpnServer.setSessionUsageMap(sessionMap);// link the map
		// transform
		if (sessions != null) {
			for (ApiSessionUsage _session : sessions) {
				SessionUsage _sessionUsage = new SessionUsage();
				_sessionUsage.setUsage(_session.getUsage());
				_sessionUsage.setLastModifyTimestamp(_session
						.getLastModifyTimestamp());
				sessionMap.put(_session.getSessionId(), _sessionUsage);
			}
		}

		return vpnServer;
	}

	private ApiUserUsage findApiUserUsage(String email, String productKey) {
		Query query = factory
				.getCurrentSession()
				.createQuery(
						" from ApiUserUsage as usage where usage.productKey = :productKey and usage.email = :email ");
		query.setString("email", email);
		query.setString("productKey", productKey);

		return (ApiUserUsage) query.uniqueResult();
	}

}
