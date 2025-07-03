package com.tcskart.order_services.dto;



public class ProductSalesDTO {
    private int productId;
    private Long totalQuantitySold;

    public ProductSalesDTO(int productId , Long totalQuantitySold) {
        this. productId =  productId;
        this.totalQuantitySold = totalQuantitySold;
    }

   
    public Long getTotalQuantitySold() {
        return totalQuantitySold;
    }

 

    public int getProductId() {
		return productId;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}


	public void setTotalQuantitySold(Long totalQuantitySold) {
        this.totalQuantitySold = totalQuantitySold;
    }
}
