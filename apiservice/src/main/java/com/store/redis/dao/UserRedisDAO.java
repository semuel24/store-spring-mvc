package com.store.redis.dao;

import java.util.HashMap;
import java.util.Map;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.store.redis.client.RedisClient;
import com.store.redis.model.SessionUsage;
import com.store.redis.model.VpnUser;
import com.store.utils.Constants;

/**
 *  redis stores user 
 * 		key: email + productKey (supports multiple services)
 * 		value:
 * 			a) user info {
 * 							email,
 * 							password, 
 * 							salt,
 * 							productKey, 
 * 							status,   --   if we disable a user, needs to re-activate by special events
 * 										   it's not about usage limit
 * 
 * 							serviceStartTimestamp,
 * 						    period, 
 * 							currentCycleEndTimestamp,
 * 
 * 							userUsageLimit,    --by promotion, this value may be increased; on the beginning of next billing cycle,
 * 												 it can be reset to default
 * 						  	totalUsageofExpiredSessions,
 * 							totalUsageofAllSessions,							
 * 						    sessionUsageMap:
 * 								[
 * 									{sessionId1:{usage1, lastModifyTimestamp1}},
 * 									{sessionId2:{usage2, lastModifyTimestamp2}},
 * 									...
 * 								]
 * 						 }
 */
@Component
public class UserRedisDAO {

	@Autowired
	@Qualifier("redisClient")
	private RedisClient redisClient;

	public static String USER_DATA_PREFIX = "/user/";

	public RedisClient getRedisClient() {
		return redisClient;
	}

	public void setRedisClient(RedisClient redisClient) {
		this.redisClient = redisClient;
	}

	public void saveOrUpdateUser(VpnUser redisUser) {
		RBucket<VpnUser> bucket = redisClient.getRedisson().getBucket(
				normalizeUserKey(redisUser));
		bucket.set(redisUser);
	}

	/**
	 * only needs email and productKey
	 */
	public void deleteUser(VpnUser redisUser) {
		RBucket<VpnUser> bucket = redisClient.getRedisson().getBucket(
				normalizeUserKey(redisUser));
		bucket.delete();
	}

	public VpnUser findUserByKey(String email, String productKey) {
		RBucket<VpnUser> bucket = redisClient.getRedisson().getBucket(
				normalizeUserKey(email, productKey));
		return bucket.get();
	}

	private String normalizeUserKey(VpnUser redisUser) {
		return normalizeUserKey(redisUser.getEmail(), redisUser.getProductKey());
	}

	private String normalizeUserKey(String email, String productKey) {
		return USER_DATA_PREFIX + email + "/" + productKey;
	}
	
	public static void main(String[] args) throws Exception {

		// get a redis connection
		RedisClient client = new RedisClient();
		Config config = new Config();
		config.useSingleServer().setAddress("127.0.0.1:6379");
		client.setRedisson(Redisson.create(config));

		UserRedisDAO dao = new UserRedisDAO();
		dao.setRedisClient(client);
		
		//mock a user
		VpnUser user = new VpnUser();
		user.setEmail("test@gmail.com");
		user.setSalt("salt");
		user.setPassword("password");
		user.setStatus(true);
		user.setProductKey(Constants.PRODUCT.FREETRIAL.getProductKey());
		user.setServiceStartTimestamp(System.currentTimeMillis());
		user.setPeriod(Constants.PERIOD.MONTHLY.getPeriod());
		user.setCurrentCycleEndTimestamp(System.currentTimeMillis()+3600000);//one hour later
		user.setUserUsageLimit(1000000000L);
		user.setTotalUsageofExpiredSessions(100000000L);
		user.setTotalUsageofAllSessions(100000000L);
		
		SessionUsage session1Usage = new SessionUsage();
		session1Usage.setUsage(10000000L);
		session1Usage.setLastModifyTimestamp(System.currentTimeMillis());
		Map<Long, SessionUsage> sessionMap = new HashMap<Long, SessionUsage>();
		Long session1Id = 1234123414L;
		sessionMap.put(session1Id, session1Usage);
		user.setSessionUsageMap(sessionMap);
		
		//can't find the user
		if(dao.findUserByKey(user.getEmail(), user.getProductKey()) != null) {
			throw new RuntimeException("");
		}
		
		//create the user
		dao.saveOrUpdateUser(user);
		
		//find the user
		if(dao.findUserByKey(user.getEmail(), user.getProductKey()) == null) {
			throw new RuntimeException("");
		}

		//delete the user
		dao.deleteUser(user);
		
		//can't find the user again
		if(dao.findUserByKey(user.getEmail(), user.getProductKey()) != null) {
			throw new RuntimeException("");
		}
		
		client.destroy();
	}
}
