package com.store.redis.dao;

import org.redisson.core.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.store.redis.client.RedisClient;

/**
 * {BLOCK_USER_PREFIX+email+productKey:blockUntilTimestamp}
 */
@Component("blockUserDAO")
public class BlockUserDAO {

	@Autowired
	@Qualifier("redisClient")
	private RedisClient redisClient;

	public static String BLOCK_USER_PREFIX = "/blockuser/";

	public RedisClient getRedisClient() {
		return redisClient;
	}

	public void setRedisClient(RedisClient redisClient) {
		this.redisClient = redisClient;
	}

	public void addBlockUser(String email, String productKey,
			Long blockUntilTimestamp) {
		RBucket<Long> bucket = redisClient.getRedisson().getBucket(
				getBlockUserKey(email, productKey));
		bucket.set(blockUntilTimestamp);
	}

	/**
	 * return true - given user is blocked
	 * 		  false - given user is not on the block list
	 */
	public Boolean verifyBlockUser(String email, String productKey) {
		RBucket<Long> bucket = redisClient.getRedisson().getBucket(
				getBlockUserKey(email, productKey));
		
		Long blockUntilTimestamp = bucket.get();
		if(blockUntilTimestamp == null) {
			return false;
		}
		
		if(System.currentTimeMillis() >= blockUntilTimestamp) {
			bucket.delete();//reset
			return false;
		}
		
		if(System.currentTimeMillis() < blockUntilTimestamp) {
			return true;
		}
		
		throw new RuntimeException("verifyBlockUser exception");
	}

	private String getBlockUserKey(String email, String productKey) {
		return BLOCK_USER_PREFIX + email + "/" + productKey;
	}
}
