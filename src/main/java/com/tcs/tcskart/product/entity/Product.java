package com.tcs.tcskart.product.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tcs.tcskart.product.utility.ProductCategory;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ProductCategory productCategory;

	@Column(nullable = false)
	private Double productRating;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<ProductImage> images;

	public List<ProductImage> getImages() {
	    return images;
	}

	public void setImages(List<ProductImage> images) {
	    this.images = images;
	}


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

	
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
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


	public Double getProductRating() {
		return productRating;
	}

	public void setProductRating(Double productRating) {
		this.productRating = productRating;
	}

}
