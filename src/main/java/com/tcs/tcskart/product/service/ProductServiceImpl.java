package com.tcs.tcskart.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.tcskart.product.entity.Order;
import com.tcs.tcskart.product.entity.OrderItem;
import com.tcs.tcskart.product.entity.Product;
import com.tcs.tcskart.product.entity.ProductReview;
import com.tcs.tcskart.product.entity.User;
import com.tcs.tcskart.product.exceptions.ProductNotFound;
import com.tcs.tcskart.product.exceptions.UserNotFound;
import com.tcs.tcskart.product.repository.OrderItemRepo;
import com.tcs.tcskart.product.repository.OrderRepo;
import com.tcs.tcskart.product.repository.ProductRepository;
import com.tcs.tcskart.product.repository.ProductReviewRepo;
import com.tcs.tcskart.product.repository.UserRepo;
import com.tcs.tcskart.product.utility.ProductNotFoundException;

@Service
public class ProductServiceImpl implements ProductService{

	 @Autowired
	   private ProductRepository productRepository;

		@Autowired
		UserRepo userRepo;
		
		@Autowired
		OrderItemRepo itemRepo;
		
		@Autowired
		OrderRepo orderRepo;
		
		@Autowired
		ProductReviewRepo productReviewRepo;
		
		@Autowired
        private ProductRepository productRepo;


	 @Autowired
	 public ProductServiceImpl(ProductRepository productRepository) {
	        this.productRepository = productRepository;
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
	     return productRepository.save(product);  
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
	public void viewProductById(Integer productId) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public Product updateProduct(Product product) {
	    List<Product> existingProducts = productRepository.findByProductName(product.getProductName());
	    
	    if (existingProducts.isEmpty()) {
	        throw new ProductNotFoundException("Product " + product.getProductName() + " does not exist.");
	    }

	    Product existingProduct = existingProducts.get(0); 
	    	    existingProduct.setQuantity(existingProduct.getQuantity() + product.getQuantity());
	    if (product.getProductRating() != null) {
	        existingProduct.setProductRating(product.getProductRating());
	    }
	    return productRepository.save(existingProduct);
	}


	@Override
	public void deleteProductByName(String productName) {
	    List<Product> products = productRepository.findByProductName(productName);
	    if (products.isEmpty()) {
	        throw new ProductNotFoundException("Product with name '" + productName + "' does not exist.");
	    }
	    productRepository.deleteAll(products);
	}


	 // Search products by keyword
    @Override
    public List<Product> searchProductsByKeyword(String keyword) {
		// TODO Auto-generated method stub
        return productRepository.findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }
    
    @Override

    // Add new review with rating 
    public String addProductReview(int userId, int productId, double rating, String reviewText) {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFound());

       
        boolean hasDeliveredOrder = false;
        List<Order> orders = orderRepo.findByUserEmail(user.getEmail());

        for (Order order : orders) {
            if ("DELIVERED".equalsIgnoreCase(order.getStatus())) {
                for (OrderItem item : order.getOrderItems()) {
                    if (item.getProduct().getProductId() == productId) {
                        hasDeliveredOrder = true;
                        break;
                    }
                }
            }
            if (hasDeliveredOrder) break;
        }

        if (!hasDeliveredOrder) {
            return "You can only review products you have received.";
        }

        Product product = productRepo.findById(productId).orElseThrow(() -> new ProductNotFound());

        ProductReview review = new ProductReview();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(rating);
        review.setReviewText(reviewText);
        productReviewRepo.save(review);

        updateProductAverageRating(productId);

        return "Product review submitted successfully and rating updated.";
    }
    @Override
    // Update an existing review
    public String updateProductReview(long reviewId, int userId, int productId, double rating, String reviewText) {
        ProductReview review = productReviewRepo.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (review.getUser().getId() != userId || review.getProduct().getProductId() != productId) {
            return "Review does not belong to the user or product.";
        }

        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFound());

      
        boolean hasDeliveredOrder = false;
        List<Order> orders = orderRepo.findByUserEmail(user.getEmail());

        for (Order order : orders) {
            if ("DELIVERED".equalsIgnoreCase(order.getStatus())) {
                for (OrderItem item : order.getOrderItems()) {
                    if (item.getProduct().getProductId() == productId) {
                        hasDeliveredOrder = true;
                        break;
                    }
                }
            }
            if (hasDeliveredOrder) break;
        }

        if (!hasDeliveredOrder) {
            return "You can only update reviews for products you have received.";
        }

        review.setRating(rating);
        review.setReviewText(reviewText);
        productReviewRepo.save(review);

        updateProductAverageRating(productId);

        return "Product review updated successfully and rating recalculated.";
    }
    @Override
    // Get all reviews for a product
    public List<ProductReview> getReviewsForProduct(int productId) {
        return productReviewRepo.findByProductProductId(productId);
    }

    private void updateProductAverageRating(int productId) {
        
        List<ProductReview> reviews = productReviewRepo.findByProductProductId(productId);
        
        // Calculate average rating 
        double average = 0;
        if (!reviews.isEmpty()) {
            double sum = 0;
            for (ProductReview review : reviews) {
                sum += review.getRating();
            }
            average = sum / reviews.size();
        }
        
        // Find the product and update its rating
        Product product = productRepo.findById(productId).orElseThrow(() -> new ProductNotFound());
        product.setProductRating(average);
        productRepo.save(product);
    }




}
