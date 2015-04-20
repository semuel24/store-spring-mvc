package com.store.db.dao.iml.mysql;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.store.db.dao.api.DeviceKeyTakenDAO;
import com.store.entity.ApiDeviceKeyTaken;

/**
 * stores which account(email) is using this device(deviceKey) We suppose one
 * device is ONLY allowed to be used by one account. If one user uses a friend's
 * device, this friend will not be able to use his own account again. In this
 * case, this friend will need to contact us to re-activate his account. On our
 * side, we will review the utilization history.
 * 
 * storage schema -------------- key: deviceKey value: email
 */
@Transactional
@Component("deviceKeyTakenDAOMysql")
public class DeviceKeyTakenDAOMysqlImpl extends StoreDAOMysqlImpl<ApiDeviceKeyTaken>
		implements DeviceKeyTakenDAO {

	// only API
	public Boolean blockDevice(String deviceKey, String email) {

		Query query = factory
				.getCurrentSession()
				.createQuery(
						" from ApiDeviceKeyTaken as taken where taken.deviceKey = :deviceKey and taken.email = :email ");
		query.setString("deviceKey", deviceKey);
		query.setString("email", email);

		@SuppressWarnings("unchecked")
		List<Object[]> entities = query.list();
		return entities.size() != 0;
	}

	public ApiDeviceKeyTaken getBlockDeviceByDeviceKey(String deviceKey) {
		Query query = factory
				.getCurrentSession()
				.createSQLQuery(
						" select * from api_device_key_taken as taken where taken.deviceKey = :deviceKey ")
				.addEntity(ApiDeviceKeyTaken.class);
		query.setString("deviceKey", deviceKey);

		@SuppressWarnings("unchecked")
		List<ApiDeviceKeyTaken> entities = (List<ApiDeviceKeyTaken>) query
				.list();

		return entities != null && entities.size() != 0 ? entities.get(0)
				: null;
	}

	public void insertNewRecord(String deviceKey, String email) {
		ApiDeviceKeyTaken taken = new ApiDeviceKeyTaken();
		taken.setDeviceKey(deviceKey);
		taken.setEmail(email);
		this.create(taken);
	}

}
