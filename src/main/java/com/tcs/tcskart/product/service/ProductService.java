package com.tcs.tcskart.product.service;

import java.util.List;

import com.tcs.tcskart.product.entity.Product;

public interface ProductService {

	Product addProduct(Product product);
	
//	void viewAllProducts();
	List<Product> viewAllProducts();
	
	void viewProductById(Integer productId);
	
	void updateProduct(Product product);
	
	void deleteProductById(Integer productId);


	List<Product> viewProductsByName(String productName);

	List<Product> searchProductsByKeyword(String keyword);
}
