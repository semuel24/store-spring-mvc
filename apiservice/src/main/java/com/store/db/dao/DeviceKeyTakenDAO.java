package com.store.db.dao;

import com.store.entity.ApiDeviceKeyTaken;

public interface DeviceKeyTakenDAO extends StoreDAO<ApiDeviceKeyTaken>{

	//only API
	public Boolean blockDevice(String deviceKey, String email) ;
	
}
