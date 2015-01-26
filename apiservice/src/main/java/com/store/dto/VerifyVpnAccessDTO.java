package com.store.dto;

public class VerifyVpnAccessDTO {

	private String email;
	private String password;
	private String incomingIp;//for test
	private String deviceKey;
	private String platform;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String toString() {
		String result = "";
		if(email != null) {
			result += "email:" + email + " | ";
		}
		if(password != null) {
			result += "password:" + password + " | ";
		}
		if(incomingIp != null) {
			result += "incomingIp:" + incomingIp;
		}
		if(deviceKey != null) {
			result += "deviceKey:" + deviceKey + " | ";
		}
		if(platform != null) {
			result += "platform:" + platform;
		}
		
		return result;
	}
}
