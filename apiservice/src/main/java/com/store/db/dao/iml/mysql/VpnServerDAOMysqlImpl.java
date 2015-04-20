package com.store.db.dao.iml.mysql;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.store.db.dao.api.VpnServerDAO;
import com.store.entity.ApiVpnServer;
import com.store.redis.client.VpnServerInfo;
import com.store.utils.Constants;

@Transactional
@Component("vpnServerDAOMysql")
public class VpnServerDAOMysqlImpl extends StoreDAOMysqlImpl<ApiVpnServer> implements
		VpnServerDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(VpnServerDAOMysqlImpl.class);

	public void saveOrUpdateVpnServer(String productKey, VpnServerInfo server) {

		ApiVpnServer vpnServer = new ApiVpnServer();
		vpnServer.setCreatetime(new Date());
		vpnServer.setEmail(server.getEmail());
		vpnServer.setIp(server.getIp());
		vpnServer.setProductkey(productKey);
		this.create(vpnServer);
	}

	public void deleteVpnServer(String productKey, String ip) {
		throw new UnsupportedOperationException(
				"VpnServerDAOImpl.deleteVpnServer()");
	}

	public String findProductKeyServerByIp(String ip) {
		Query query = factory.getCurrentSession().createQuery(
				" from ApiVpnServer as server where server.ip = :ip");
		query.setString("ip", ip);

		return query.uniqueResult() != null ? ((ApiVpnServer) query
				.uniqueResult()).getProductkey() : null;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Map<String, VpnServerInfo>> findAllVpnServers() {
		Query query = factory.getCurrentSession()
				.createSQLQuery("select * from api_vpn_server")
				.addEntity(ApiVpnServer.class);

		List<ApiVpnServer> list = (List<ApiVpnServer>) query.list();
		Map<String, Map<String, VpnServerInfo>> maps = new HashMap<String, Map<String, VpnServerInfo>>();
		maps.put(Constants.PRODUCT.FREETRIAL.getProductKey(),
				new HashMap<String, VpnServerInfo>());
		maps.put(Constants.PRODUCT.DEDICATE_MEMBERSHIP.getProductKey(),
				new HashMap<String, VpnServerInfo>());
		maps.put(Constants.PRODUCT.SHARED_MEMBERSHIP.getProductKey(),
				new HashMap<String, VpnServerInfo>());

		if (list == null) {
			return maps;
		}

		for (ApiVpnServer server : list) {
			VpnServerInfo info = new VpnServerInfo();
			info.setIp(server.getIp());
			if (Constants.PRODUCT.FREETRIAL.getProductKey().equalsIgnoreCase(
					server.getProductkey())) {
				info.setEmail(server.getEmail());
				maps.get(Constants.PRODUCT.FREETRIAL.getProductKey()).put(
						server.getIp(), info);
			} else if (Constants.PRODUCT.DEDICATE_MEMBERSHIP.getProductKey()
					.equalsIgnoreCase(server.getProductkey())) {
				maps.get(Constants.PRODUCT.DEDICATE_MEMBERSHIP.getProductKey())
						.put(server.getIp(), info);
			} else if (Constants.PRODUCT.SHARED_MEMBERSHIP.getProductKey()
					.equalsIgnoreCase(server.getProductkey())) {
				maps.get(Constants.PRODUCT.SHARED_MEMBERSHIP.getProductKey())
						.put(server.getIp(), info);
			} else {
				throw new RuntimeException("unknown product key:"
						+ server.getProductkey());
			}
		}
		return maps;
	}

}
