package com.tcskart.cartservice.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tcskart.cartservice.util.ProductCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;

	@Column(nullable = false)
	private String productName;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private Double productPrice;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false)
	private ProductCategory productCategory;

	@Column(nullable = false)
	private Double productRating;

	@OneToMany(mappedBy = "product")
	@JsonBackReference
	private List<CartItem> cartItems;

	public Product() {

	}

	public Product(String productName, String description, Double productPrice, Integer quantity,
			ProductCategory productCategory, Double productRating) {
		super();
		this.productName = productName;
		this.description = description;
		this.productPrice = productPrice;
		this.quantity = quantity;
		this.productCategory = productCategory;
		this.productRating = productRating;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public Product(ProductCategory productCategory) {
		super();
		this.productCategory = productCategory;
	}

	public Double getProductRating() {
		return productRating;
	}

	public void setProductRating(Double productRating) {
		this.productRating = productRating;
	}

}
