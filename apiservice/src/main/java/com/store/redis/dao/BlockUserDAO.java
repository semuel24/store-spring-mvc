package com.store.redis.dao;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.store.redis.client.RedisClient;
import com.store.utils.Constants;

/**
 * {BLOCK_USER_PREFIX+email+productKey:blockUntilTimestamp}
 * 
 * Basically, block user list is an eventually-sync view of user data. Its data should
 * not update back to user's table. 
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
	
	public static void main(String[] args) throws Exception {

		// get a redis connection
		RedisClient client = new RedisClient();
		Config config = new Config();
		config.useSingleServer().setAddress("127.0.0.1:6379");
		client.setRedisson(Redisson.create(config));

		BlockUserDAO dao = new BlockUserDAO();
		dao.setRedisClient(client);

		// insert into block list
		dao.addBlockUser("test@gmail.com",
				Constants.PRODUCT.FREETRIAL.getProductKey(),
				System.currentTimeMillis());

		// vefiry in block list
		dao.verifyBlockUser("test@gmail.com",
				Constants.PRODUCT.FREETRIAL.getProductKey());
		
		client.destroy();
	}
}
