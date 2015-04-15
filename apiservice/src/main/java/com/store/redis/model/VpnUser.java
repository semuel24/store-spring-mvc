package com.store.redis.model;

import java.util.Map;

public class VpnUser {

	private String email;
	private String password;
	private String salt;
	private Boolean status;
	private String productKey;

	private Long serviceStartTimestamp;
	private String period;
	private Long currentCycleEndTimestamp;

	private Long userUsageLimit;
	private Long totalUsageofExpiredSessions;
	private Long totalUsageofAllSessions;

	private Map<Long, SessionUsage> sessionUsageMap;

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

	public Long getUserUsageLimit() {
		return userUsageLimit;
	}

	public void setUserUsageLimit(Long userUsageLimit) {
		this.userUsageLimit = userUsageLimit;
	}

	public Long getTotalUsageofExpiredSessions() {
		return totalUsageofExpiredSessions;
	}

	public void setTotalUsageofExpiredSessions(Long totalUsageofExpiredSessions) {
		this.totalUsageofExpiredSessions = totalUsageofExpiredSessions;
	}

	public Long getTotalUsageofAllSessions() {
		return totalUsageofAllSessions;
	}

	public void setTotalUsageofAllSessions(Long totalUsageofAllSessions) {
		this.totalUsageofAllSessions = totalUsageofAllSessions;
	}

	public Map<Long, SessionUsage> getSessionUsageMap() {
		return sessionUsageMap;
	}

	public void setSessionUsageMap(Map<Long, SessionUsage> sessionUsageMap) {
		this.sessionUsageMap = sessionUsageMap;
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

	public Long getCurrentCycleEndTimestamp() {
		return currentCycleEndTimestamp;
	}

	public void setCurrentCycleEndTimestamp(Long currentCycleEndTimestamp) {
		this.currentCycleEndTimestamp = currentCycleEndTimestamp;
	}

}
