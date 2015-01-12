package com.store.dto;

public class ReportUsageDTO{

	private String email;
	private String vpnServerIp;//for test
	private Long upUsage;
	private Long downUsage;
	private String userIp;//for future
	private Long sessionId;//session start timestamp
	
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
	public Long getSessionId() {
		return sessionId;
	}
	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}
	
	public String toString() {
		String result = "";
		if(email != null) {
			result += "email:" + email + " | ";
		}
		if(vpnServerIp != null) {
			result += "vpnServerIp:" + vpnServerIp + " | ";
		}
		if(upUsage != null) {
			result += "upUsage:" + upUsage + " | ";
		}
		if(downUsage != null) {
			result += "downUsage:" + downUsage + " | ";
		}
		if(userIp != null) {
			result += "userIp:" + userIp + " | ";
		}
		if(sessionId != null) {
			result += "sessionId:" + sessionId + " | ";
		}
		return result;
	}
}
