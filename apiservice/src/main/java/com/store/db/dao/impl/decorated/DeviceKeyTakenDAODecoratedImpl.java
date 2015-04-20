package com.store.db.dao.impl.decorated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@Component("deviceKeyTakenDAODecorated")
public class DeviceKeyTakenDAODecoratedImpl implements DeviceKeyTakenDAO {

	@Autowired
	@Qualifier("deviceKeyTakenDAOMysql")
	private DeviceKeyTakenDAO deviceKeyTakenDAO;

	// only API
	public Boolean blockDevice(String deviceKey, String email) {
		return deviceKeyTakenDAO.blockDevice(deviceKey, email);
	}

	public ApiDeviceKeyTaken getBlockDeviceByDeviceKey(String deviceKey) {
		return deviceKeyTakenDAO.getBlockDeviceByDeviceKey(deviceKey);
	}
	
	public void insertNewRecord(String deviceKey, String email) {
		deviceKeyTakenDAO.insertNewRecord(deviceKey, email);
	}
}
