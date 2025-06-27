package com.tcs.tcskart.product.dto;

import com.tcs.tcskart.product.utility.ProductCategory;

public class ProductDetails {

    private String productName;
    private String description;
    private Double productPrice;
    private ProductCategory productCategory;
    private Double productRating;
    private String availabilityStatus;

    
    
      public ProductDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductDetails(String productName, String description, Double productPrice, ProductCategory productCategory, Double productRating, String availabilityStatus) {
        this.productName = productName;
        this.description = description;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productRating = productRating;
        this.availabilityStatus = availabilityStatus;
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

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Double getProductRating() {
        return productRating;
    }

    public void setProductRating(Double productRating) {
        this.productRating = productRating;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}
