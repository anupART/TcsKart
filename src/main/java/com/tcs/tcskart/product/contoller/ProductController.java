package com.tcs.tcskart.product.contoller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.tcskart.product.dto.ProductDetails;
import com.tcs.tcskart.product.entity.Product;
import com.tcs.tcskart.product.entity.ProductReview;
import com.tcs.tcskart.product.service.ProductService;
import com.tcs.tcskart.product.utility.ProductNotFoundException;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    //Admin
    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        try {
            Product addedProduct = productService.addProduct(product);
            if (addedProduct == null) {
                return new ResponseEntity<>("Product with name '" + product.getProductName() + "' already exists.", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Product added successfully.", HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    //Users-all details
    @GetMapping("/allproducts")
    public List<Product> getAllProducts() {
        return productService.viewAllProducts();
    }
    
    //Users by product name
    @GetMapping("/name/{productName}")
    public ResponseEntity<Object> getProductsByName(@PathVariable String productName) {
        try {
            List<Product> products = productService.viewProductsByName(productName);
            return new ResponseEntity<>(products, HttpStatus.OK); 
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    //Users by product name to check available
    @GetMapping("/details/{productName}")
    public ResponseEntity<Object> getProductDetailsByName(@PathVariable String productName) {
        try {
            List<Product> products = productService.viewProductsByName(productName);
            if (products.isEmpty()) {
                return new ResponseEntity<>("No products found with the name: " + productName, HttpStatus.NOT_FOUND);
            }
            List<ProductDetails> productDetailsDTOList = new ArrayList<>();
            for (Product product : products) {
                String availabilityStatus = (product.getQuantity() > 0) ? "Available" : "Not Available";
                ProductDetails dto = new ProductDetails(
                        product.getProductName(),
                        product.getDescription(),
                        product.getProductPrice(),
                        product.getProductCategory(),
                        product.getProductRating(),
                        availabilityStatus
                );

                productDetailsDTOList.add(dto);
            }
            return new ResponseEntity<>(productDetailsDTOList, HttpStatus.OK);
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    //To Search Product with keyword
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Product>> searchProducts(@PathVariable String keyword) {
        List<Product> products = productService.searchProductsByKeyword(keyword);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(product);
            return new ResponseEntity<>("Product updated successfully.", HttpStatus.OK);
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{productName}")
    public ResponseEntity<String> deleteProductByName(@PathVariable String productName) {
        try {
            productService.deleteProductByName(productName);  
            return new ResponseEntity<>("Product(s) deleted successfully.", HttpStatus.OK);
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
  


    @PostMapping("/review/{userId}/{productId}/{rating}")
    public String addProductReview(
            @PathVariable int userId,
            @PathVariable int productId,
            @PathVariable double rating,
            @RequestBody String reviewText) {
        return productService.addProductReview(userId, productId, rating, reviewText);
    }

    @PutMapping("/review/{reviewId}/{userId}/{productId}/{rating}")
    public String updateProductReview(
            @PathVariable long reviewId,
            @PathVariable int userId,
            @PathVariable int productId,
            @PathVariable double rating,
            @RequestBody String reviewText) {
        return productService.updateProductReview(reviewId, userId, productId, rating, reviewText);
    }

    @GetMapping("/reviews/{productId}")
    public List<ProductReview> getReviews(@PathVariable int productId) {
        return productService.getReviewsForProduct(productId);
    }




}


