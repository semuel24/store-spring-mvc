package com.store.db.dao.iml.mysql;

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

import com.store.db.dao.VpnServerDAO;
import com.store.entity.VpnServer;
import com.store.redis.client.RedisClient;
import com.store.redis.client.VpnServerInfo;
import com.store.utils.Constants;

@Component
public class ServerRedisDAOImpl extends StoreDAOImpl<VpnServer> implements VpnServerDAO{

	
	private static final Logger logger = LoggerFactory
			.getLogger(ServerRedisDAOImpl.class);

	public static String SERVER_LIST_ROOT_KEY_PREFIX = "/vpnserver/";
	public static String SERVER_LIST_ROOT_KEY = SERVER_LIST_ROOT_KEY_PREFIX
			+ "rootkey";

	
	public void saveOrUpdateVpnServer(String productKey, VpnServerInfo server) {

		return;
	}

	public void deleteVpnServer(String productKey, String ip) {

		
	}

	public String findProductKeyServerByIp(String ip) {
		
		return null;
	}

	public Map<String, Map<String, VpnServerInfo>> findAllVpnServers() {
		RBucket<Map<String, Map<String, VpnServerInfo>>> bucket = redisClient
				.getRedisson().getBucket(SERVER_LIST_ROOT_KEY);
		return bucket.get();
	}

}
