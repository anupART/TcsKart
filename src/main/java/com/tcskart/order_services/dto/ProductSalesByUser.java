package com.tcskart.order_services.dto;

import com.tcskart.order_services.bean.Product;

public class ProductSalesByUser {
	 
	    private Product product;
	    private Long NoOfUser;
	    
	    public ProductSalesByUser()
	    {
	    	
	    }
		public Product getProduct() {
			return product;
		}
		public void setProduct(Product product) {
			this.product = product;
		}
		public Long getNoOfUser() {
			return NoOfUser;
		}
		public void setNoOfUser(Long noOfUser) {
			NoOfUser = noOfUser;
		}
		public ProductSalesByUser(Product product, Long noOfUser) {
			super();
			this.product = product;
			NoOfUser = noOfUser;
		}
	  
}
