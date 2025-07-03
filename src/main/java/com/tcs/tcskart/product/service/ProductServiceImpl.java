package com.tcs.tcskart.product.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import com.tcs.tcskart.product.entity.*;
import com.tcs.tcskart.product.exceptions.*;
import com.tcs.tcskart.product.repository.*;
import com.tcs.tcskart.product.utility.ProductCategory;
import com.tcs.tcskart.product.utility.ProductNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    public ProductRepository productRepository;

    @Autowired
    UserRepo userRepo;

    @Autowired
    OrderItemRepo itemRepo;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    ProductReviewRepo productReviewRepo;

    // Constructor for testing
    public ProductServiceImpl(ProductRepository productRepository, UserRepo userRepo,
                              OrderItemRepo itemRepo, OrderRepo orderRepo,
                              ProductReviewRepo productReviewRepo) {
        this.productRepository = productRepository;
        this.userRepo = userRepo;
        this.itemRepo = itemRepo;
        this.orderRepo = orderRepo;
        this.productReviewRepo = productReviewRepo;
    }

    @Override
    public List<Product> viewAllProducts() {
        return productRepository.findAll()
                .stream()
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

    @Override
    public void deleteProductByName(String productName) {
        List<Product> products = productRepository.findByProductName(productName);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("Product with name '" + productName + "' does not exist.");
        }
        productRepository.deleteAll(products);
    }

    @Override
    public ResponseEntity<String> deleteById(Integer productId) {
        if (productId < 0) {
            throw new InValidProductIdException();
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        productRepository.delete(product);

        return new ResponseEntity<>("Product Deletion Successful.", HttpStatus.OK);
    }

    @Override
    public List<Product> searchByProductCategory(ProductCategory productCategory) {
        return productRepository.findByProductCategory(productCategory);
    }

    @Override
    public List<Product> searchProductsByKeyword(String keyword) {
        return productRepository.findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }

    @Override
    public Page<Product> getPaginatedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    public boolean isValidImageUrl(String url) {
        return url != null && (url.toLowerCase().endsWith(".jpg") || url.toLowerCase().endsWith(".jpeg") || url.toLowerCase().endsWith(".png"));
    }

    @Override
    public Product updateProductById(Integer productId, Product product) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        if (product.getProductName() != null) existingProduct.setProductName(product.getProductName());
        if (product.getDescription() != null) existingProduct.setDescription(product.getDescription());
        if (product.getProductPrice() != null) existingProduct.setProductPrice(product.getProductPrice());
        if (product.getQuantity() != null) existingProduct.setQuantity(product.getQuantity());
        if (product.getProductCategory() != null) existingProduct.setProductCategory(product.getProductCategory());
        if (product.getProductRating() != null) existingProduct.setProductRating(product.getProductRating());

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
        boolean hasDeliveredOrder = orders.stream()
                .anyMatch(order -> "DELIVERED".equalsIgnoreCase(order.getStatus()) &&
                        order.getOrderItems().stream()
                                .anyMatch(item -> item.getProduct().getProductId() == productId));

        if (!hasDeliveredOrder) {
            return "You can only review products you have received.";
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found for review."));

        ProductReview review = new ProductReview();
        review.setProduct(product);
        review.setRating(rating);
        review.setReviewText(reviewText);
        productReviewRepo.save(review);

        updateProductAverageRating(productId);

        return "Product review submitted successfully and rating updated.";
    }

    @Override
    public List<ProductReview> getReviewsByProductId(int productId) {
        return productReviewRepo.findByProductProductId(productId);
    }

    private void updateProductAverageRating(int productId) {
        List<ProductReview> reviews = productReviewRepo.findByProductProductId(productId);
        double avg = reviews.stream().mapToDouble(ProductReview::getRating).average().orElse(0);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found during rating update."));
        product.setProductRating(avg);
        productRepository.save(product);
    }

    @Override
    public List<Product> getProductsSortedByRating() {
        return productRepository.findAllByOrderByProductRatingDesc();
    }
}
