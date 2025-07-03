package com.tcskart.order_services.bean;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "`order`")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String status;
	
	private LocalDate orderDate;
	
	private String Address;
	
	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
	private List<OrderItem> orderItems;
	
	private double totalAmount;

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Order(int id, String status, LocalDate orderDate, User user, List<OrderItem> orderItems) {
		super();
		this.id = id;
		this.status = status;
		this.orderDate = orderDate;
		this.user = user;
		this.orderItems = orderItems;
	}

	public Order() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	

	public void setUser(User user) {
		this.user = user;
	}



	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}


	
}