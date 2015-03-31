package com.store.db.dao;

import com.store.entity.DeviceKeyTaken;

public interface DeviceKeyTakenDAO extends StoreDAO<DeviceKeyTaken>{

	//only API
	public Boolean blockDevice(String deviceKey, String email) ;
	
}
