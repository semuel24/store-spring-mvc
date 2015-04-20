package com.store.db.dao.impl.decorated;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.store.db.dao.api.VpnServerDAO;
import com.store.redis.client.VpnServerInfo;

@Transactional
@Component("vpnServerDAODecorated")
public class VpnServerDAODecoratedImpl implements VpnServerDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(VpnServerDAODecoratedImpl.class);

	@Autowired
	@Qualifier("vpnServerDAOMysql")
	private VpnServerDAO vpnServerDAO;

	public void saveOrUpdateVpnServer(String productKey, VpnServerInfo server) {
		vpnServerDAO.saveOrUpdateVpnServer(productKey, server);
	}

	public void deleteVpnServer(String productKey, String ip) {
		throw new UnsupportedOperationException(
				"VpnServerDAODecoratedImpl.deleteVpnServer()");
	}

	public String findProductKeyServerByIp(String ip) {
		return vpnServerDAO.findProductKeyServerByIp(ip);
	}

	public Map<String, Map<String, VpnServerInfo>> findAllVpnServers() {
		return vpnServerDAO.findAllVpnServers();
	}

}
