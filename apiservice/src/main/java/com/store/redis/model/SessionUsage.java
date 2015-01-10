package com.store.redis.model;

public class SessionUsage {
	private Long usage;
	private Long lastModifyTimestamp;

	public Long getUsage() {
		return usage;
	}

	public void setUsage(Long usage) {
		this.usage = usage;
	}

	public Long getLastModifyTimestamp() {
		return lastModifyTimestamp;
	}

	public void setLastModifyTimestamp(Long lastModifyTimestamp) {
		this.lastModifyTimestamp = lastModifyTimestamp;
	}
}
