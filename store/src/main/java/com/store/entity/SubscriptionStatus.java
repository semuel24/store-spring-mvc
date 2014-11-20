package com.store.entity;

// Generated Nov 18, 2014 12:29:08 AM by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * SubscriptionStatus generated by hbm2java
 */
@Entity
@Table(name = "subscription_status", catalog = "mydb", uniqueConstraints = @UniqueConstraint(columnNames = {
		"user_id", "product_group_id" }))
public class SubscriptionStatus implements java.io.Serializable {

	private Integer id;
	private User user;
	private ProductGroup productGroup;
	private Product product;
	private int balance;
	private byte subscriptionStatus;
	private Date lastmodifytime;

	public SubscriptionStatus() {
	}

	public SubscriptionStatus(User user, ProductGroup productGroup,
			Product product, int balance, byte subscriptionStatus) {
		this.user = user;
		this.productGroup = productGroup;
		this.product = product;
		this.balance = balance;
		this.subscriptionStatus = subscriptionStatus;
	}

	public SubscriptionStatus(User user, ProductGroup productGroup,
			Product product, int balance, byte subscriptionStatus,
			Date lastmodifytime) {
		this.user = user;
		this.productGroup = productGroup;
		this.product = product;
		this.balance = balance;
		this.subscriptionStatus = subscriptionStatus;
		this.lastmodifytime = lastmodifytime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_group_id", nullable = false)
	public ProductGroup getProductGroup() {
		return this.productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name = "balance", nullable = false)
	public int getBalance() {
		return this.balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	@Column(name = "subscription_status", nullable = false)
	public byte getSubscriptionStatus() {
		return this.subscriptionStatus;
	}

	public void setSubscriptionStatus(byte subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastmodifytime", length = 19)
	public Date getLastmodifytime() {
		return this.lastmodifytime;
	}

	public void setLastmodifytime(Date lastmodifytime) {
		this.lastmodifytime = lastmodifytime;
	}

}