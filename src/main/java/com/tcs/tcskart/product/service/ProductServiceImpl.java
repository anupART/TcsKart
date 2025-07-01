package com.tcs.tcskart.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tcs.tcskart.product.entity.Product;
import com.tcs.tcskart.product.entity.ProductImage;
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
	 public List<Product> viewAllProducts() {
	     List<Product> products = productRepository.findAll();
	     return products.stream()
	                    .filter(product -> product.getQuantity() > 0)  
	                    .collect(Collectors.toList());
	 }
	 

	@Override
	public List<Product> viewProductsByName(String productName) {
	    List<Product> products = productRepository.findByProductName(productName);
	    if (products.isEmpty()) {
	        throw new ProductNotFoundException("Product " + productName + " does not exist.");
	    }
	    return products;
	}



	@Override
	public Product addProduct(Product product) {
	    if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
	        throw new IllegalArgumentException("Enter Something in Product Name.");
	    }
	    List<Product> existingProducts = productRepository.findByProductName(product.getProductName());
	    if (!existingProducts.isEmpty()) {
	        return null;
	    }
	    if (product.getProductRating() == null) {
	        product.setProductRating(0.0);
	    }

	    if (product.getImages() != null) {
	        for (ProductImage img : product.getImages()) {
	            if (!isValidImageUrl(img.getImageUrl())) {
	                throw new IllegalArgumentException("Only JPG or PNG image URLs are allowed.");
	            }
	            img.setProduct(product);
	        }
	    }
	    return productRepository.save(product);
	}

	// Delete by Product by Name
	@Override
	public void deleteProductByName(String productName) {
	    List<Product> products = productRepository.findByProductName(productName);
	    if (products.isEmpty()) {
	        throw new ProductNotFoundException("Product with name '" + productName + "' does not exist.");
	    }
	    productRepository.deleteAll(products);
	}
	
	// Delete by Product id 
	@Override
	public void deleteProductByID(Integer productId) {
	    Optional<Product> product = productRepository.findById(productId);
	    if (product.isEmpty()) {
	        throw new ProductNotFoundException("Product with ID '" + productId + "' does not exist.");
	    }
	    productRepository.delete(product.get());  
	}



	 // Search products by keyword
    @Override
    public List<Product> searchProductsByKeyword(String keyword) {
		// TODO Auto-generated method stub
        return productRepository.findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }
    
    // This for the Pagination
    @Override
    public Page<Product> getPaginatedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable); 
    }
    
    private boolean isValidImageUrl(String url) {
        return url != null && (url.toLowerCase().endsWith(".jpg") || url.toLowerCase().endsWith(".jpeg") || url.toLowerCase().endsWith(".png"));
    }

    // Update the product by ID
    @Override
    public Product updateProductById(Integer productId, Product product) {
		// TODO Auto-generated method stub

        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
        if (product.getProductName() != null) {
            existingProduct.setProductName(product.getProductName());
        }
        if (product.getDescription() != null) {
            existingProduct.setDescription(product.getDescription());
        }
        if (product.getProductPrice() != null) {
            existingProduct.setProductPrice(product.getProductPrice());
        }
        if (product.getQuantity() != null) {
            existingProduct.setQuantity(product.getQuantity());
        }
        if (product.getProductCategory() != null) {
            existingProduct.setProductCategory(product.getProductCategory());
        }
        if (product.getProductRating() != null) {
            existingProduct.setProductRating(product.getProductRating());
        }
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            for (ProductImage img : product.getImages()) {
                if (!isValidImage(img.getImageUrl())) {
                    throw new IllegalArgumentException("Only JPG or PNG image URLs are allowed.");
                }
                img.setProduct(existingProduct);
            }
            existingProduct.getImages().clear();
            existingProduct.getImages().addAll(product.getImages());
        }
        return productRepository.save(existingProduct);
    }

    private boolean isValidImage(String imageUrl) {
        return imageUrl.endsWith(".jpg") || imageUrl.endsWith(".png");
    }


}
