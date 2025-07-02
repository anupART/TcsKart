package com.tcskart.cartservice.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Wishlist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer wishlistId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private User user;
	
	public Wishlist(Integer wishlistId, User user, Product product) {
		super();
		this.wishlistId = wishlistId;
		this.user = user;
		this.product = product;
	}

	@ManyToOne
	private Product product;
	
	public Wishlist() {}

	public Wishlist(User user, Product product) {
		super();
		this.user = user;
		this.product = product;
	}

	public Integer getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(Integer wishlistId) {
		this.wishlistId = wishlistId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	
	
}
