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
}
