package com.store.service;

import java.util.Map;
import com.store.dto.AddVpnServerDTO;
import com.store.redis.client.VpnServerInfo;

public interface VpnServerService {

	public void handleAddServer(AddVpnServerDTO dto);
	public void handleDeleteServer(String productKey, String ip);
	public Map<String, Map<String, VpnServerInfo>> handleFindAllServers();
}
