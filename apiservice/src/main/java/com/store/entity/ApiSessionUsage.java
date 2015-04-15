package com.store.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "api_session_usage")
public class ApiSessionUsage {

	private int id;
	private int userUsageId;
	private Long sessionId;
	private Long usage;
	private Long lastModifyTimestamp;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "user_usage_id")
	public int getUserUsageId() {
		return userUsageId;
	}

	public void setUserUsageId(int userUsageId) {
		this.userUsageId = userUsageId;
	}

	@Column(name = "sessionid")
	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	@Column(name = "usage")
	public Long getUsage() {
		return usage;
	}

	public void setUsage(Long usage) {
		this.usage = usage;
	}

	@Column(name = "lastModifyTimestamp")
	public Long getLastModifyTimestamp() {
		return lastModifyTimestamp;
	}

	public void setLastModifyTimestamp(Long lastModifyTimestamp) {
		this.lastModifyTimestamp = lastModifyTimestamp;
	}

}
