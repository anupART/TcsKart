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
	
	private String email;
	
	private int productId;
	
	public Wishlist(Integer wishlistId, String email, int productId) {
		super();
		this.wishlistId = wishlistId;
		this.email = email;
		this.productId = productId;
	}
	
	public Wishlist() {}

	public Wishlist(String email, int productId) {
		super();
		this. email= email;
		this.productId = productId;
	}

	public Integer getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(Integer wishlistId) {
		this.wishlistId = wishlistId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	

	
	
}
