package com.store.result;

import java.util.Map;
import com.store.redis.client.VpnServerInfo;

public class ServerListResult extends StatusResult {

	private Map<String, Map<String, VpnServerInfo>> servers;
	
	public Map<String, Map<String, VpnServerInfo>> getServers() {
		return servers;
	}

	public void setServers(Map<String, Map<String, VpnServerInfo>> servers) {
		this.servers = servers;
	}

	public ServerListResult(String _status) {
		super(_status);
	}
}
