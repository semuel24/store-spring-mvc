package com.store.dto;

public class AddVpnServerDTO {

	private String ip;
	private String productKey;
	private String email;//only for dedicate product
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getProductKey() {
		return productKey;
	}
	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String toString() {
		
		String result = "";
		if(email != null) {
			result += "email:" + email + " | ";
		}
		if(ip != null) {
			result += " ip:" + ip + " | ";
		}
		if(productKey != null) {
			result += " productKey:" + productKey;
		}
		return result;
	}
}
