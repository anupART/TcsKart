package com.tcs.tcskart.product.dto;

import com.tcs.tcskart.product.utility.ProductCategory;

public class ProductShare {
		private Integer productId;
	 	private String productName;
	    private String description;
	    public ProductShare(Integer productId, String productName, String description, Double productPrice,
				ProductCategory productCategory, Double productRating, Integer quantity) {
			super();
			this.productId = productId;
			this.productName = productName;
			this.description = description;
			this.productPrice = productPrice;
			this.productCategory = productCategory;
			this.productRating = productRating;
			this.quantity = quantity;
		}
		public Integer getProductId() {
			return productId;
		}
		public void setProductId(Integer productId) {
			this.productId = productId;
		}
		private Double productPrice;
	    private ProductCategory productCategory;
	    private Double productRating;
	    private Integer quantity;
	   
	    
		public ProductShare(String productName, String description, Double productPrice,
				ProductCategory productCategory, Double productRating, Integer quantity) {
			super();
			this.productName = productName;
			this.description = description;
			this.productPrice = productPrice;
			this.productCategory = productCategory;
			this.productRating = productRating;
			this.quantity = quantity;
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
		public Integer getQuantity() {
			return quantity;
		}
		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
	    
	    
}
