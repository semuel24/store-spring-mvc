package com.store.db.dao.iml.mysql;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.store.db.dao.BlockUserDAO;
import com.store.entity.BlockUser;
import com.store.redis.client.RedisClient;
import com.store.utils.Constants;

/**
 * {BLOCK_USER_PREFIX+email+productKey:blockUntilTimestamp}
 * 
 * Basically, block user list is an eventually-sync view of user data. Its data should
 * not update back to user's table. 
 */
@Component("blockUserDAO")
public class BlockUserDAOImpl extends StoreDAOImpl<BlockUser> implements BlockUserDAO{

	public static String BLOCK_USER_PREFIX = "/blockuser/";

	public void addBlockUser(String email, String productKey,
			Long blockUntilTimestamp) {
		
	}

	/**
	 * return true - given user is blocked
	 * 		  false - given user is not on the block list
	 */
	public Boolean verifyBlockUser(String email, String productKey) {
		
		throw new RuntimeException("verifyBlockUser exception");
	}

	private String getBlockUserKey(String email, String productKey) {
		return BLOCK_USER_PREFIX + email + "/" + productKey;
	}
}
