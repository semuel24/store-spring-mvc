package com.store.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "device")
public class Device {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "deviceKey")
	private String deviceKey;

	@Column(name = "nabiPassEnabled")
	private Boolean nabiPassEnabled;

	@Column(name = "isDeleted")
	private Boolean isDeleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getDeviceKey() {
		return deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public Boolean getNabiPassEnabled() {
		return nabiPassEnabled;
	}

	public void setNabiPassEnabled(Boolean nabiPassEnabled) {
		this.nabiPassEnabled = nabiPassEnabled;
	}

}
