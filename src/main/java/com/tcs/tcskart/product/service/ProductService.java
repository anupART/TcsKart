package com.tcs.tcskart.product.service;

import org.springframework.http.ResponseEntity;

import com.tcs.tcskart.product.entity.Product;



public interface ProductService {
	 void updateProduct(Product product);
	 void viewProductById(Integer productId);
	 void viewAllProducts();
	 void addProduct(Product product);
	ResponseEntity<String> deleteById(Integer productId);
}
