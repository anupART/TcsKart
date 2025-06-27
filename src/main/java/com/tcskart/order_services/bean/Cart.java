package com.tcskart.order_services.bean;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Cart {
	
	@Id
	private int id;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Product product;
	
	private int Quantity;
}

