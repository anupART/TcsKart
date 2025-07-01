package com.tcs.tcskart.product.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tcs.tcskart.product.entity.Product;

public interface ProductService {

	Product addProduct(Product product);
	
//	void viewAllProducts();
	List<Product> viewAllProducts();
	
//	void viewProductById(Integer productId);
	
//	Product updateProduct(Product product);


	List<Product> viewProductsByName(String productName);

	List<Product> searchProductsByKeyword(String keyword);

	void deleteProductByName(String productName);

	Page<Product> getPaginatedProducts(int page, int size);

	Product updateProductById(Integer productId, Product product);

	void deleteProductByID(Integer productId);
}
