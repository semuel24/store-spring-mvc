package com.store.entity;

// Generated Nov 18, 2014 12:29:08 AM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * PaymentGateway generated by hbm2java
 */
@Entity
@Table(name = "payment_gateway", uniqueConstraints = @UniqueConstraint(columnNames = "gatewaykey"))
public class PaymentGateway implements java.io.Serializable {

	private int id;
	private String gatewaykey;
	private String desc;
	private Set<OrderRecord> orderRecords = new HashSet<OrderRecord>(0);

	public PaymentGateway() {
	}

	public PaymentGateway(int id, String gatewaykey, String desc) {
		this.id = id;
		this.gatewaykey = gatewaykey;
		this.desc = desc;
	}

	public PaymentGateway(int id, String gatewaykey, String desc,
			Set<OrderRecord> orderRecords) {
		this.id = id;
		this.gatewaykey = gatewaykey;
		this.desc = desc;
		this.orderRecords = orderRecords;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "gatewaykey", unique = true, nullable = false, length = 45)
	public String getGatewaykey() {
		return this.gatewaykey;
	}

	public void setGatewaykey(String gatewaykey) {
		this.gatewaykey = gatewaykey;
	}

	@Column(name = "desc", nullable = false)
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentGateway")
	public Set<OrderRecord> getOrderRecords() {
		return this.orderRecords;
	}

	public void setOrderRecords(Set<OrderRecord> orderRecords) {
		this.orderRecords = orderRecords;
	}

}
