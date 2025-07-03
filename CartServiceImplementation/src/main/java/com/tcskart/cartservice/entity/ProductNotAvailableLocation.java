package com.tcskart.cartservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductNotAvailableLocation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer pincode;
	
	
	private int productId;

	public ProductNotAvailableLocation() {}
	
	public ProductNotAvailableLocation(Integer id, Integer pincode, int productId) {
		this.id = id;
		this.pincode = pincode;
		this.productId = productId;
	}

	public ProductNotAvailableLocation(Integer pincode, int productId) {
		super();
		this.pincode = pincode;
		this.productId = productId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPincode() {
		return pincode;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	
	
	
	
}
