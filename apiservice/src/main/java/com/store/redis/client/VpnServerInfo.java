package com.store.redis.client;

public class VpnServerInfo {

	private String ip;
	private String email;//if dedicate server
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
