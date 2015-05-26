package com.store.dto;

public class DeviceControlDTO {

	private String email;
	private String incomingIp;// for test
	private String deviceKey;
	private String platform;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIncomingIp() {
		return incomingIp;
	}

	public void setIncomingIp(String incomingIp) {
		this.incomingIp = incomingIp;
	}

	public String getDeviceKey() {
		return deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
}
