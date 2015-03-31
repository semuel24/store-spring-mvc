package com.store.db.dao.iml.mysql;

import java.util.UUID;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.store.db.dao.DeviceKeyTakenDAO;
import com.store.entity.DeviceKeyTaken;
import com.store.redis.client.RedisClient;
import com.store.redis.model.DeviceTaken;

/**
 * stores which account(email) is using this device(deviceKey)
 * We suppose one device is ONLY allowed to be used by one account.
 * If one user uses a friend's device, this friend will not be able to 
 * use his own account again. In this case, this friend will need to contact
 * us to re-activate his account. On our side, we will review the utilization
 * history.
 * 
 * storage schema
 * --------------
 * key: deviceKey
 * value: email
 */
@Component("deviceKeyTakenDAO")
public class DeviceKeyTakenDAOImpl extends StoreDAOImpl<DeviceKeyTaken> implements DeviceKeyTakenDAO{

	private static String DEVICEKEY_TAKEN_MAP_PREFIX = "/devicekey/taken/";

	//only API
	public Boolean blockDevice(String deviceKey, String email) {
		return null;
	}
	
}
