package com.store.db.dao.iml.mysql;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.store.db.dao.DeviceKeyTakenDAO;
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
@Component("deviceKeyTakenDAO")
public class DeviceKeyTakenDAOImpl extends StoreDAOImpl<ApiDeviceKeyTaken>
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

}
