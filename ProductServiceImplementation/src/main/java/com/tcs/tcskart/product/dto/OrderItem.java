package com.tcs.tcskart.product.dto;



import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


public class OrderItem {


	private long id;
	
	private int quantity;
	
	private double price;

	private Order order;
	

	private int productId;

	public OrderItem(long id, int quantity, double price, Order order, int productId ) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.price = price;
		this.order = order;
		this.productId = productId;
	}

	public OrderItem() {
		super();
	}



	public void setId(long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	



	public void setOrder(Order order) {
		this.order = order;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}


	
}