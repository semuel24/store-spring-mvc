package com.store.db.dao;

import java.util.Map;
import com.store.entity.ApiVpnServer;
import com.store.redis.client.VpnServerInfo;

public interface VpnServerDAO extends StoreDAO<ApiVpnServer>{

	public void saveOrUpdateVpnServer(String productKey, VpnServerInfo server);
	public void deleteVpnServer(String productKey, String ip);
	public String findProductKeyServerByIp(String ip) ;
	public Map<String, Map<String, VpnServerInfo>> findAllVpnServers();
}
