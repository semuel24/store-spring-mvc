package com.store.redis.dao;

import java.util.HashMap;
import java.util.Map;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RBucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.store.redis.client.RedisClient;
import com.store.redis.client.VpnServerInfo;
import com.store.utils.Constants;

/**
 * redis stores vpn servers in a nested map
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
 */
@Component
public class ServerRedisDAO {

	@Autowired
	@Qualifier("redisClient")
	private RedisClient redisClient;

	private static final Logger logger = LoggerFactory
			.getLogger(ServerRedisDAO.class);

	public static String SERVER_LIST_ROOT_KEY_PREFIX = "/vpnserver/";
	public static String SERVER_LIST_ROOT_KEY = SERVER_LIST_ROOT_KEY_PREFIX
			+ "rootkey";

	public RedisClient getRedisClient() {
		return redisClient;
	}

	public void setRedisClient(RedisClient redisClient) {
		this.redisClient = redisClient;
	}

	public void saveOrUpdateVpnServer(String productKey, VpnServerInfo server) {

		RBucket<Map<String, Map<String, VpnServerInfo>>> bucket = redisClient
				.getRedisson().getBucket(SERVER_LIST_ROOT_KEY);
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

		RBucket<Map<String, Map<String, VpnServerInfo>>> bucket = redisClient
				.getRedisson().getBucket(SERVER_LIST_ROOT_KEY);

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
		RBucket<Map<String, Map<String, VpnServerInfo>>> bucket = redisClient
				.getRedisson().getBucket(SERVER_LIST_ROOT_KEY);
		if (bucket == null) {
			if (logger.isErrorEnabled()) {
				logger.error("### bucket is null ip: " + ip);
			}
			return null;
		}

		Map<String, Map<String, VpnServerInfo>> vpnServerMap = bucket.get();
		if (vpnServerMap == null) {
			if (logger.isErrorEnabled()) {
				logger.error("### vpnServerMap is null ip: " + ip);
			}
			return null;
		}

		String[] productKeys = { Constants.PRODUCT.FREETRIAL.getProductKey(),
				Constants.PRODUCT.SHARED_MEMBERSHIP.getProductKey(),
				Constants.PRODUCT.DEDICATE_MEMBERSHIP.getProductKey() };

		for (String productKey : productKeys) {

			if (logger.isInfoEnabled()) {
				logger.info("### product key:" + productKey);
			}

			if (vpnServerMap.containsKey(productKey)) {
				Map<String, VpnServerInfo> vpnServerMapofGivenKey = vpnServerMap
						.get(productKey);
				if (vpnServerMapofGivenKey.containsKey(ip)) {
					if (logger.isInfoEnabled()) {
						logger.info("### the target product key is "
								+ productKey);
					}
					return productKey;// found the product key
				}
			}
		}

		if (logger.isInfoEnabled()) {
			logger.info("### the target product key is null");
		}
		return null;
	}

	public Map<String, Map<String, VpnServerInfo>> findAllVpnServers() {
		RBucket<Map<String, Map<String, VpnServerInfo>>> bucket = redisClient
				.getRedisson().getBucket(SERVER_LIST_ROOT_KEY);
		return bucket.get();
	}

	public static void main(String[] args) throws Exception {

		// get a redis connection
		RedisClient client = new RedisClient();
		Config config = new Config();
		config.useSingleServer().setAddress("127.0.0.1:6379");
		client.setRedisson(Redisson.create(config));

		ServerRedisDAO dao = new ServerRedisDAO();
		dao.setRedisClient(client);

		// find a server
		if (dao.findProductKeyServerByIp("1.1.1.1") != null) {
			throw new RuntimeException("");
		}

		// add a vpn server
		VpnServerInfo vpn1 = new VpnServerInfo();
		vpn1.setEmail("test@gmail.com");
		vpn1.setIp("1.1.1.1");
		dao.saveOrUpdateVpnServer(Constants.PRODUCT.FREETRIAL.getProductKey(),
				vpn1);

		if (dao.findProductKeyServerByIp(vpn1.getIp()) == null) {
			throw new RuntimeException("");
		}

		// delete the vpn server
		dao.deleteVpnServer(Constants.PRODUCT.FREETRIAL.getProductKey(),
				"1.1.1.1");

		// get the vpn server
		if (dao.findProductKeyServerByIp(vpn1.getIp()) != null) {
			throw new RuntimeException("");
		}

		// get the whole list of vpn servers
		Map<String, Map<String, VpnServerInfo>> map = dao.findAllVpnServers();
	}
}
