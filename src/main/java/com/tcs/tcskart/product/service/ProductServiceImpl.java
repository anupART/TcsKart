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

import com.tcs.tcskart.product.entity.Order;
import com.tcs.tcskart.product.entity.OrderItem;
import com.tcs.tcskart.product.entity.Product;
import com.tcs.tcskart.product.entity.ProductImage;
import com.tcs.tcskart.product.entity.ProductReview;
import com.tcs.tcskart.product.entity.User;
import com.tcs.tcskart.product.exceptions.ProductNotFound;
import com.tcs.tcskart.product.exceptions.UserNotFound;
import com.tcs.tcskart.product.repository.OrderItemRepo;
import com.tcs.tcskart.product.repository.OrderRepo;

//import com.tcs.tcskart.product.entity.Order;
//import com.tcs.tcskart.product.entity.OrderItem;
//import com.tcs.tcskart.product.entity.Product;

//import com.tcs.tcskart.product.entity.ProductImage;

//import com.tcs.tcskart.product.entity.ProductReview;
//import com.tcs.tcskart.product.entity.User;
//import com.tcs.tcskart.product.exceptions.ProductNotFound;
//import com.tcs.tcskart.product.exceptions.UserNotFound;
//import com.tcs.tcskart.product.repository.OrderItemRepo;
//import com.tcs.tcskart.product.repository.OrderRepo;
//>>>>>>> origin/product-service
import com.tcs.tcskart.product.repository.ProductRepository;
import com.tcs.tcskart.product.repository.ProductReviewRepo;
import com.tcs.tcskart.product.repository.UserRepo;
//import com.tcs.tcskart.product.repository.ProductReviewRepo;

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
		
	

	// Constructor injection - for testing
	 @Autowired
	 public ProductServiceImpl(ProductRepository productRepository) {
	        this.productRepository = productRepository;
	  }
	 


	 // To View all the products
	 @Override
	 public List<Product> viewAllProducts() {
	     List<Product> products = productRepository.findAll();
	     return products.stream()
	                    .filter(product -> product.getQuantity() > 0)  
	                    .collect(Collectors.toList());
	 }
	 

	 // To View Product By Name
	@Override
	public List<Product> viewProductsByName(String productName) {
	    List<Product> products = productRepository.findByProductName(productName);
	    if (products.isEmpty()) {
	        throw new ProductNotFoundException("Product " + productName + " does not exist.");
	    }
	    return products;
	}


	// To add the product
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


//add product reviews
    @Override
    public String addProductReview(String email, int productId, double rating, String reviewText) {
       
        if (rating < 1 || rating > 5) {
            return "Rating must be between 1 and 5.";
        }

        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UserNotFound();
        }

        List<Order> orders = orderRepo.findByUserEmail(email);
        boolean hasDeliveredOrder = false;
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

        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFound());

        ProductReview review = new ProductReview();
//        review.setUser(user);
        review.setProduct(product);
        review.setRating(rating);
        review.setReviewText(reviewText);
        productReviewRepo.save(review);

        updateProductAverageRating(productId);

        return "Product review submitted successfully and rating updated.";
    }
//get all reviews of a product
    @Override
    public List<ProductReview> getReviewsByProductId(int productId) {
        return productReviewRepo.findByProductProductId(productId);
    }

    // average rating
    private void updateProductAverageRating(int productId) {
        List<ProductReview> allReviews = productReviewRepo.findByProductProductId(productId);
        double averageRating = allReviews.stream()
                .mapToDouble(ProductReview::getRating)
                .average()
                .orElse(0);

        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFound());
        product.setProductRating(averageRating);
        productRepository.save(product);
    }


     // Get products sorted by rating
     public List<Product> getProductsSortedByRating() {
         return productRepository.findAllByOrderByProductRatingDesc();
     }


}
