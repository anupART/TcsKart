package com.tcskart.order_services.exception;

public class ProductNotAvailable extends RuntimeException {
	
	public ProductNotAvailable(String productName) {
        super("The product '" + productName + "' is not available in the selected location.");
    }

}
