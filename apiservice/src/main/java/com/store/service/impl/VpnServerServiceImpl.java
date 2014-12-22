package com.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.store.dto.AddVpnServerDTO;
import com.store.redis.client.RedisClient;
import com.store.redis.client.VpnServerInfo;
import com.store.service.VpnServerService;

@Component("vpnServerService")
public class VpnServerServiceImpl implements VpnServerService {

	@Autowired
	private RedisClient redisClient;
	
	public void handleAddServer(AddVpnServerDTO dto) {
		VpnServerInfo vpnServer = new VpnServerInfo();
		vpnServer.setEmail(dto.getEmail());
		vpnServer.setIp(dto.getIp());
		redisClient.saveOrUpdateVpnServer(dto.getProductKey(), vpnServer);
	}

	public void handleDeleteServer(String productKey, String ip) {
		redisClient.deleteVpnServer(productKey, ip);
	}
}
