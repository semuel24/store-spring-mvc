package com.store.dto;

public class VerifyVpnAccessDTO {

	private String email;
	private String password;
	private String incomingIp;
	
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
}
