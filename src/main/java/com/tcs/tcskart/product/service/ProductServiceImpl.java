package com.tcs.tcskart.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.tcskart.product.entity.Product;
import com.tcs.tcskart.product.repository.ProductRepository;
import com.tcs.tcskart.product.utility.ProductNotFoundException;

@Service
public class ProductServiceImpl implements ProductService{

	 @Autowired
	   private ProductRepository productRepository;

	 @Autowired
	 public ProductServiceImpl(ProductRepository productRepository) {
	        this.productRepository = productRepository;
	  }
	 
	 @Override
	    public Product addProduct(Product product) {
	        List<Product> existingProducts = productRepository.findByProductName(product.getProductName());
	        if (!existingProducts.isEmpty()) {
	            return null;
	        }
	        if (product.getProductRating() == null) {
	            product.setProductRating(0.0);
	        }
	        return productRepository.save(product);
	    }
	 
	 @Override
	public List<Product> viewAllProducts() {
	    return productRepository.findAll();
	}

	@Override
	public List<Product> viewProductsByName(String productName) {
	    List<Product> products = productRepository.findByProductName(productName);
	    if (products.isEmpty()) {
	        throw new ProductNotFoundException("Product with name '" + productName + "' does not exist.");
	    }
	    return products;
	}


	@Override
	public void viewProductById(Integer productId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProductById(Integer productId) {
		// TODO Auto-generated method stub
		
	}

}
