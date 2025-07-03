package com.tcskart.order_services.dto;

import java.time.LocalDate;
import java.util.List;



public class OrderDto {

	private String email;
	private String status;
	private LocalDate orderDate;
	private String address;
	private int pincode;
	
	public int getPincode() {
		return pincode;
	}


	public void setPincode(int pincode) {
		this.pincode = pincode;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}

	private List<OrderItemDto> orderItems;
	
	public OrderDto()
	{
		
	}
	
	



	public OrderDto(String email, List<OrderItemDto> orderItems,String address,int pincode) {
		super();
		this.email = email;
		this.address=address;
		this.orderItems = orderItems;
		this.pincode=pincode;
	}


	public OrderDto(String status, LocalDate orderDate, String email, List<OrderItemDto> orderItems) {
		super();
		this.status = status;
		this.orderDate = orderDate;
		this.email = email;
		this.orderItems = orderItems;
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



	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public List<OrderItemDto> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDto> orderItems) {
		this.orderItems = orderItems;
	}

	
	
}
