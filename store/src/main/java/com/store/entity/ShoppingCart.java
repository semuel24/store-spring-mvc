package com.store.entity;

// Generated Nov 18, 2014 12:29:08 AM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ShoppingCart generated by hbm2java
 */
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart implements java.io.Serializable {

	private int id;
	private User user;
	private Set<ShoppingCartHasProduct> shoppingCartHasProducts = new HashSet<ShoppingCartHasProduct>(
			0);

	public ShoppingCart() {
	}

	public ShoppingCart(int id, User user) {
		this.id = id;
		this.user = user;
	}

	public ShoppingCart(int id, User user,
			Set<ShoppingCartHasProduct> shoppingCartHasProducts) {
		this.id = id;
		this.user = user;
		this.shoppingCartHasProducts = shoppingCartHasProducts;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shoppingCart")
	public Set<ShoppingCartHasProduct> getShoppingCartHasProducts() {
		return this.shoppingCartHasProducts;
	}

	public void setShoppingCartHasProducts(
			Set<ShoppingCartHasProduct> shoppingCartHasProducts) {
		this.shoppingCartHasProducts = shoppingCartHasProducts;
	}

}
