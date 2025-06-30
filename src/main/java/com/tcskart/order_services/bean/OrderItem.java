package com.tcskart.order_services.bean;



import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private int quantity;
	
	private double price;
	
	@ManyToOne
	private Order order;
	
	@ManyToOne
	private Product product;

	public Product getProduct() {
		return product;
	}

	public OrderItem(long id, int quantity, double price, Order order, Product product) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.price = price;
		this.order = order;
		this.product = product;
	}

	public OrderItem() {
		super();
	}

	public long getId() {
		return id;
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


	public void setProduct(Product product) {
		this.product = product;
	}
	
}