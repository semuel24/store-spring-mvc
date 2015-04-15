package com.store.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "api_block_user")
public class ApiBlockUser {

	private int id;
	private String email;
	private String product;
	private Date createtime;
	private Date blockuntiltimestamp;

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

	@Column(name = "product")
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "blockuntiltimestamp")
	public Date getBlockuntiltimestamp() {
		return blockuntiltimestamp;
	}

	public void setBlockuntiltimestamp(Date blockuntiltimestamp) {
		this.blockuntiltimestamp = blockuntiltimestamp;
	}
}
