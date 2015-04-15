package com.store.service.impl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.store.db.dao.VpnServerDAO;
import com.store.dto.AddVpnServerDTO;
import com.store.redis.client.VpnServerInfo;
import com.store.service.VpnServerService;

@Component("vpnServerService")
public class VpnServerServiceImpl implements VpnServerService {

	@Autowired
	private VpnServerDAO serverRedisDAO;
	
	public void handleAddServer(AddVpnServerDTO dto) {
		VpnServerInfo vpnServer = new VpnServerInfo();
		vpnServer.setEmail(dto.getEmail());
		vpnServer.setIp(dto.getIp());
		serverRedisDAO.saveOrUpdateVpnServer(dto.getProductKey(), vpnServer);
	}

	public void handleDeleteServer(String productKey, String ip) {
		serverRedisDAO.deleteVpnServer(productKey, ip);
	}
	
	public Map<String, Map<String, VpnServerInfo>> handleFindAllServers() {
		return serverRedisDAO.findAllVpnServers();
	}
	
}
