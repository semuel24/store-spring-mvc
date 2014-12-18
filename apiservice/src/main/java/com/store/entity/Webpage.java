package com.store.entity;

// Generated Nov 18, 2014 12:29:08 AM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Webpage generated by hbm2java
 */
@Entity
@Table(name = "webpage")
public class Webpage implements java.io.Serializable {

	private Integer id;
	private String url;
	private String method;
	private String desc;
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);

	public Webpage() {
	}

	public Webpage(String url, String method, String desc) {
		this.url = url;
		this.method = method;
		this.desc = desc;
	}

	public Webpage(String url, String method, String desc,
			Set<UserRole> userRoles) {
		this.url = url;
		this.method = method;
		this.desc = desc;
		this.userRoles = userRoles;
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

	@Column(name = "url", nullable = false)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "method", nullable = false, length = 10)
	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Column(name = "desc", nullable = false)
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role_access_page", catalog = "mydb", joinColumns = { @JoinColumn(name = "webpage_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "user_role_id", nullable = false, updatable = false) })
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

}