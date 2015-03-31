package com.store.db.dao.iml.mysql;

import java.util.HashMap;
import java.util.Map;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.store.db.dao.UserUsageDAO;
import com.store.entity.UserUsage;
import com.store.redis.client.RedisClient;
import com.store.redis.model.SessionUsage;
import com.store.redis.model.VpnUser;
import com.store.utils.Constants;

@Component
public class UserRedisDAOImpl extends StoreDAOImpl<UserUsage> implements UserUsageDAO{

	public static String USER_DATA_PREFIX = "/user/";

	

	public void saveOrUpdateUser(VpnUser redisUser) {
		
	}

	/**
	 * only needs email and productKey
	 */
	public void deleteUser(VpnUser redisUser) {
		
	}

	public VpnUser findUserByKey(String email, String productKey) {
		return null;
	}

	
}
