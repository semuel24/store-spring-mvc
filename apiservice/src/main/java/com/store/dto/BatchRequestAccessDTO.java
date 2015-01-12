package com.store.dto;

import java.util.List;

public class BatchRequestAccessDTO {

	private String vpnServerIp;//for test
	private List<String> emails;
	
	public String getVpnServerIp() {
		return vpnServerIp;
	}
	public void setVpnServerIp(String vpnServerIp) {
		this.vpnServerIp = vpnServerIp;
	}
	public List<String> getEmails() {
		return emails;
	}
	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
	public String toString() {
		String result = "";
		if(vpnServerIp != null) {
			result += "vpnServerIp:" + vpnServerIp + " | ";
		}
		if(emails != null) {
			result += "emails:[";
			for(String email : emails) {
				result += email + ",";
			}
			result += "] ";
		}
		return result;
	}
}
