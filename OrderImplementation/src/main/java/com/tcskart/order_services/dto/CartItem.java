package com.tcskart.order_services.dto;

public class CartItem {

	private Integer id;
	
	private Integer quantity;
	
	private Cart cart;
	
	private Integer productId;

	public CartItem(Integer id, Integer quantity, Cart cart, Integer productId) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.cart = cart;
		this.productId = productId;
	}
	
	public CartItem(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}


	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

}
