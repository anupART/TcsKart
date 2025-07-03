package com.tcskart.order_services.dto;



public class OrderItemDto {
    
	private int productId;
	
	private int quantity;
	
	@Override
	public String toString() {
		return "OrderItemDto [productId=" + productId + ", quantity=" + quantity + ", price=" + price + "]";
	}

	private double price;
	
	public OrderItemDto()
	{
		
	}



	public int getProductid() {
		return productId;
	}



	public void setProductid(int productid) {
		productId = productid;
	}



	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public OrderItemDto(int productId, int quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
	
  

}
