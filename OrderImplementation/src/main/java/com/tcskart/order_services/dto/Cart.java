package com.tcskart.order_services.dto;


import java.util.List;

public class Cart {
	
	private Integer id;
	
	private String email;
	
	private List<CartItem> cartItems;

	public Cart(int id, String email, List<CartItem> cartItems) {
		super();
		this.id = id;
		this.email = email;
		this.cartItems = cartItems;
	}
	
	public Cart(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	
	
	
}
