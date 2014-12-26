package com.store.redis.client;

import java.util.HashMap;
import java.util.Map;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RBucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.store.utils.Constants;

//http://redis.io/topics/persistence
//https://www.linode.com/docs/databases/redis/redis-on-ubuntu-12-04-precise-pangolin
//refer to https://github.com/mrniko/redisson/wiki/Usage-examples
/**
 * Precondition:
 * 1. mysql still has all data, except user usage data for usage sensitive products
 * 2. redis stores user 
 * 		key: email + productkey (supports multiple services)
 * 		value:
 * 			a) user info (email, password, salt, status, productkey,
 * 						  usage, serviceStartTimestamp, period, currentCycleEndTimestamp)
 * 			b) server IP list
 * 
 * 3. redis stores vpn servers in a nested map
 * 	{SERVER_LIST_ROOT_KEY:
 *       [
 *       	{
 *       		Constants.PRODUCT.FREETRIAL:[{ip,email...},..]
 *       	},
 *       	{
 *       		Constants.PRODUCT.SHARED_MEMBERSHIP:[...]
 *       	},
 *       	{
 *       		Constants.PRODUCT.DEDICATE_MEMBERSHIP:[...]
 *       	}
 *       ]
 * 	}
 * com.store.redis.client.RedisClient
 * 	  
 */
@Component
public class RedisClient implements InitializingBean, DisposableBean {
	
	private static final Logger logger = LoggerFactory
			.getLogger(RedisClient.class);

	public static String SERVER_LIST_ROOT_KEY_PREFIX = "/vpnserver/";
	public static String SERVER_LIST_ROOT_KEY = SERVER_LIST_ROOT_KEY_PREFIX
			+ "rootkey";
	public static String USER_DATA_PREFIX = "/user/";

	private Redisson redisson;
	
//	private String redisServerAddress = "127.0.0.1:6379";
	@Autowired
	@Qualifier("redisServerAddress")
	private String redisServerAddress;

	public void saveOrUpdateUser(VpnUser redisUser) {
		RBucket<VpnUser> bucket = redisson
				.getBucket(normalizeUserKey(redisUser));
		bucket.set(redisUser);
	}

	/**
	 * only needs email and productKey
	 */
	public void deleteUser(VpnUser redisUser) {
		RBucket<VpnUser> bucket = redisson
				.getBucket(normalizeUserKey(redisUser));
		bucket.delete();
	}

	public VpnUser findUserByKey(String email, String productKey) {
		RBucket<VpnUser> bucket = redisson.getBucket(normalizeUserKey(email,
				productKey));
		return bucket.get();
	}

	public void saveOrUpdateVpnServer(String productKey, VpnServerInfo server) {

		RBucket<Map<String, Map<String, VpnServerInfo>>> bucket = redisson
				.getBucket(SERVER_LIST_ROOT_KEY);
		Map<String, Map<String, VpnServerInfo>> vpnServerMap = bucket.get();
		if (vpnServerMap == null) {
			vpnServerMap = new HashMap<String, Map<String, VpnServerInfo>>();
			vpnServerMap.put(Constants.PRODUCT.FREETRIAL.getProductKey(),
					new HashMap<String, VpnServerInfo>());
			vpnServerMap.put(
					Constants.PRODUCT.SHARED_MEMBERSHIP.getProductKey(),
					new HashMap<String, VpnServerInfo>());
			vpnServerMap.put(
					Constants.PRODUCT.DEDICATE_MEMBERSHIP.getProductKey(),
					new HashMap<String, VpnServerInfo>());
		}

		Map<String, VpnServerInfo> vpnServerMapofProductKey = vpnServerMap
				.get(productKey);
		if (vpnServerMapofProductKey == null) {
			vpnServerMapofProductKey = new HashMap<String, VpnServerInfo>();
		}

		if (!vpnServerMapofProductKey.containsKey(server.getIp())) {
			vpnServerMapofProductKey.put(server.getIp(), server);
		}

		bucket.set(vpnServerMap);
	}

	public void deleteVpnServer(String productKey, String ip) {
		
		RBucket<Map<String, Map<String, VpnServerInfo>>> bucket = redisson
				.getBucket(SERVER_LIST_ROOT_KEY);
		
		Map<String, Map<String, VpnServerInfo>> vpnServerMap = bucket.get();

		if (vpnServerMap == null) {
			if (logger.isErrorEnabled()) {
				logger.error("Vpn server map is null in deleteVpnServer.");
			}
		}

		Map<String, VpnServerInfo> vpnServerMapofProductKey = vpnServerMap
				.get(productKey);
		if (vpnServerMapofProductKey == null) {
			if (logger.isErrorEnabled()) {
				logger.error("Vpn server of product key:" + productKey
						+ "map is missing in deleteVpnServer.");
			}
			return;
		}

		VpnServerInfo server = vpnServerMapofProductKey.remove(ip);
		vpnServerMap.put(productKey, vpnServerMapofProductKey);
		bucket.set(vpnServerMap);
		if (server == null) {
			if (logger.isErrorEnabled()) {
				logger.error("Vpn server ip:" + ip + "of product key:"
						+ productKey + "map is missing in deleteVpnServer.");
			}
		}
	}

	public String findProductKeyServerByIp(String ip) {
		// by brute force
		RBucket<Map<String, Map<String, VpnServerInfo>>> bucket = redisson
				.getBucket(SERVER_LIST_ROOT_KEY);
		if (bucket == null) {
			if(logger.isErrorEnabled()) {
				logger.error("### bucket is null ip: " + ip);
			}
			return null;
		}

		Map<String, Map<String, VpnServerInfo>> vpnServerMap = bucket.get();
		if (vpnServerMap == null) {
			if(logger.isErrorEnabled()) {
				logger.error("### vpnServerMap is null ip: " + ip);
			}
			return null;
		}

		String[] productKeys = { Constants.PRODUCT.FREETRIAL.getProductKey(),
				Constants.PRODUCT.SHARED_MEMBERSHIP.getProductKey(),
				Constants.PRODUCT.DEDICATE_MEMBERSHIP.getProductKey() };

		for (String productKey : productKeys) {
			
			if(logger.isErrorEnabled()) {
				logger.error("### product key:" + productKey);
			}
			
			if (vpnServerMap.containsKey(productKey)) {
				Map<String, VpnServerInfo> vpnServerMapofGivenKey = vpnServerMap
						.get(productKey);
				if (vpnServerMapofGivenKey.containsKey(ip)) {
					if(logger.isErrorEnabled()) {
						logger.error("### the target product key is " + productKey);
					}
					return productKey;// found the product key
				}
			}
		}

		if(logger.isErrorEnabled()) {
			logger.error("### the target product key is null");
		}
		return null;
	}

	public Map<String, Map<String, VpnServerInfo>> findAllVpnServers() {
		RBucket<Map<String, Map<String, VpnServerInfo>>> bucket = redisson
				.getBucket(SERVER_LIST_ROOT_KEY);
		return bucket.get();
	}

	public void afterPropertiesSet() {
		Config config = new Config();
		config.useSingleServer().setAddress(redisServerAddress);
		redisson = Redisson.create(config);
	}

	public void destroy() throws Exception {
		redisson.shutdown();
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
		client.afterPropertiesSet();
		
		client.findProductKeyServerByIp("1.1.1.1");

		// add a vpn server
		VpnServerInfo vpn1 = new VpnServerInfo();
		vpn1.setEmail("test@gmail.com");
		vpn1.setIp("1.1.1.1");
		client.saveOrUpdateVpnServer(
				Constants.PRODUCT.FREETRIAL.getProductKey(), vpn1);
	
		//delete the vpn server
		client.deleteVpnServer(Constants.PRODUCT.FREETRIAL.getProductKey(), "1.1.1.1");
		
		// get the vpn server
//		String productKey2 = client.findProductKeyServerByIp(vpn1.getIp());

		// get the whole list of vpn servers
		Map<String, Map<String, VpnServerInfo>> map = client
				.findAllVpnServers();
	}

}
