package com.tcs.tcskart.product.service;

import com.tcs.tcskart.product.entity.Product;

public interface ProductService {

	void addProduct(Product product);
	
	void viewAllProducts();
	
	void viewProductById(Integer productId);
	
	void updateProduct(Product product);
	
	void deleteProductById(Integer productId);
}
