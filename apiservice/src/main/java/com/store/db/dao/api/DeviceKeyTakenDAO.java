package com.store.db.dao.api;

import com.store.entity.ApiDeviceKeyTaken;

public interface DeviceKeyTakenDAO{

	//only API
	public Boolean blockDevice(String deviceKey, String email) ;
	public ApiDeviceKeyTaken getBlockDeviceByDeviceKey(String deviceKey);
	public void insertNewRecord(String deviceKey, String email);
	
}
