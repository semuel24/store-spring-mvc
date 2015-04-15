package com.store.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "api_user_usage")
public class ApiUserUsage {

	private int id;
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
	private Date createtime;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "salt")
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(name = "status")
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Column(name = "productkey")
	public String getProductKey() {
		return productKey;
	}

	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}

	@Column(name = "serviceStartTimestamp")
	public Long getServiceStartTimestamp() {
		return serviceStartTimestamp;
	}

	public void setServiceStartTimestamp(Long serviceStartTimestamp) {
		this.serviceStartTimestamp = serviceStartTimestamp;
	}

	@Column(name = "period")
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	@Column(name = "currentCycleEndTimestamp")
	public Long getCurrentCycleEndTimestamp() {
		return currentCycleEndTimestamp;
	}

	public void setCurrentCycleEndTimestamp(Long currentCycleEndTimestamp) {
		this.currentCycleEndTimestamp = currentCycleEndTimestamp;
	}

	@Column(name = "userUsageLimit")
	public Long getUserUsageLimit() {
		return userUsageLimit;
	}

	public void setUserUsageLimit(Long userUsageLimit) {
		this.userUsageLimit = userUsageLimit;
	}

	@Column(name = "totalUsageofExpiredSessions")
	public Long getTotalUsageofExpiredSessions() {
		return totalUsageofExpiredSessions;
	}

	public void setTotalUsageofExpiredSessions(Long totalUsageofExpiredSessions) {
		this.totalUsageofExpiredSessions = totalUsageofExpiredSessions;
	}

	@Column(name = "totalUsageofAllSessions")
	public Long getTotalUsageofAllSessions() {
		return totalUsageofAllSessions;
	}

	public void setTotalUsageofAllSessions(Long totalUsageofAllSessions) {
		this.totalUsageofAllSessions = totalUsageofAllSessions;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
