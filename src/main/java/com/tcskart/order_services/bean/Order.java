package com.tcskart.order_services.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Order {
	
	@Id
	private int Orderid;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Product product;
	
	private String Address;
	
	private String Status;
	
	private int Quantity;
	
	private String Payment;
	
	private double Amount;
	
	

}
