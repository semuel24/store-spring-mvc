package com.store.dto;

public class AddorUpdateUserDTO {

	private String email;
	private String password;
	private String salt;
	private Boolean status;
	private String productKey;
	private Long serviceStartTimestamp;
	private String period;

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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getProductKey() {
		return productKey;
	}

	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}

	public Long getServiceStartTimestamp() {
		return serviceStartTimestamp;
	}

	public void setServiceStartTimestamp(Long serviceStartTimestamp) {
		this.serviceStartTimestamp = serviceStartTimestamp;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	public String toString() {
		String result = "";
		if(email != null) {
			result += "email:" + email + " | ";
		}
		if(password != null) {
			result += "password:" + password + " | ";
		}
		if(salt != null) {
			result += "salt:" + salt + " | ";
		}
		if(status != null) {
			result += "status:" + status + " | ";
		}
		if(productKey != null) {
			result += "productKey:" + productKey + " | ";
		}
		if(serviceStartTimestamp != null) {
			result += "serviceStartTimestamp:" + serviceStartTimestamp + " | ";
		}
		if(period != null) {
			result += "period:" + period;
		}
		
		return result; 
	}
}
