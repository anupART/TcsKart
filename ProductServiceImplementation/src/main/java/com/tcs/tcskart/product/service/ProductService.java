package com.tcs.tcskart.product.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tcs.tcskart.product.dto.ProductShare;
import com.tcs.tcskart.product.entity.Product;
import com.tcs.tcskart.product.entity.ProductReview;
//import com.tcs.tcskart.product.entity.ProductReview;

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
	public String addProductReview(String email, int productId, double rating, String reviewText);
	public List<ProductReview> getReviewsByProductId(int productId);
	  public List<Product> getProductsSortedByRating();
	  
	  ProductShare getDetailsByProductId(Integer id);
	  
	  void updateProductByQuantity(Integer productId, Integer quantity);
}
