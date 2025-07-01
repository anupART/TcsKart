package com.tcs.tcskart.product.service;

import java.util.List;

import com.tcs.tcskart.product.entity.Product;
import com.tcs.tcskart.product.entity.ProductReview;

public interface ProductService {

	Product addProduct(Product product);
	
//	void viewAllProducts();
	List<Product> viewAllProducts();
	
	void viewProductById(Integer productId);
	
	Product updateProduct(Product product);


	List<Product> viewProductsByName(String productName);

	List<Product> searchProductsByKeyword(String keyword);

	void deleteProductByName(String productName);
	
	public String addProductReview(int userId, int productId, double rating, String reviewText);

	String updateProductReview(long reviewId, int userId, int productId, double rating, String reviewText);

	List<ProductReview> getReviewsForProduct(int productId);
}
