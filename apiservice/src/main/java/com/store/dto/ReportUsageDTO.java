package com.store.dto;

public class ReportUsageDTO{

	private String email;
	private String vpnServerIp;//for test
	private Long upUsage;
	private Long downUsage;
	private String userIp;//for future
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getVpnServerIp() {
		return vpnServerIp;
	}
	public void setVpnServerIp(String vpnServerIp) {
		this.vpnServerIp = vpnServerIp;
	}
	public Long getUpUsage() {
		return upUsage;
	}
	public void setUpUsage(Long upUsage) {
		this.upUsage = upUsage;
	}
	public Long getDownUsage() {
		return downUsage;
	}
	public void setDownUsage(Long downUsage) {
		this.downUsage = downUsage;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
}
