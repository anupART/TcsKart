package com.tcskart.order_services.dto;



public class ProductSalesByUser {
	 
	    private Integer productId;
	    private Long NoOfUser;
	    
	    public ProductSalesByUser()
	    {
	    	
	    }
	
		public Long getNoOfUser() {
			return NoOfUser;
		}
		public void setNoOfUser(Long noOfUser) {
			NoOfUser = noOfUser;
		}
		public ProductSalesByUser(Integer productId, Long noOfUser) {
			super();
			this.productId = productId;
			NoOfUser = noOfUser;
		}

		public Integer getProductId() {
			return productId;
		}

		public void setProductId(Integer productId) {
			this.productId = productId;
		}
	  
}
