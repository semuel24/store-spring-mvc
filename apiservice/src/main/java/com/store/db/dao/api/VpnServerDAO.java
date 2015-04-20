package com.store.db.dao.api;

import java.util.Map;
import com.store.redis.client.VpnServerInfo;

public interface VpnServerDAO {

	public void saveOrUpdateVpnServer(String productKey, VpnServerInfo server);

	public void deleteVpnServer(String productKey, String ip);

	public String findProductKeyServerByIp(String ip);

	public Map<String, Map<String, VpnServerInfo>> findAllVpnServers();
}
