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
	
	@ManyToOne
	private Product product;

	public ProductNotAvailableLocation() {}
	
	public ProductNotAvailableLocation(Integer id, Integer pincode, Product product) {
		this.id = id;
		this.pincode = pincode;
		this.product = product;
	}

	public ProductNotAvailableLocation(Integer pincode, Product product) {
		super();
		this.pincode = pincode;
		this.product = product;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
}
