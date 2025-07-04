package com.tcs.tcskart.product.contoller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.tcs.tcskart.product.dto.ProductDetails;
import com.tcs.tcskart.product.dto.ProductShare;
import com.tcs.tcskart.product.entity.Product;
import com.tcs.tcskart.product.entity.ProductReview;
import com.tcs.tcskart.product.service.ProductService;
import com.tcs.tcskart.product.utility.ProductNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/products")
@Tag(name = "Product Controller", description = "Manages product catalog, reviews, search, pagination, and stock updates")
public class ProductController {

    @Autowired
    ProductService productService;

    @Operation(summary = "Add a new product (Admin only)")
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        try {
            Product addedProduct = productService.addProduct(product);
            if (addedProduct == null) {
                return new ResponseEntity<>("Product with name '" + product.getProductName() + "' already exists.", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Product added successfully.", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get all available products")
    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.viewAllProducts();
    }

    @Operation(summary = "Get products by name")
    @GetMapping("/name/{productName}")
    public ResponseEntity<Object> getProductsByName(@PathVariable String productName) {
        try {
            List<Product> products = productService.viewProductsByName(productName);
            return new ResponseEntity<>(products, HttpStatus.OK); 
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get product details including availability by name")
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
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Search products by keyword")
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Product>> searchProducts(@PathVariable String keyword) {
        List<Product> products = productService.searchProductsByKeyword(keyword);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Update product by ID")
    @PutMapping("/update/{productId}")
    public ResponseEntity<String> updateProductById(@PathVariable Integer productId, @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProductById(productId, product);
            return new ResponseEntity<>("Product updated successfully.", HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete product by name")
    @DeleteMapping("/delete/{productName}")
    public ResponseEntity<String> deleteProductByName(@PathVariable String productName) {
        try {
            productService.deleteProductByName(productName);  
            return new ResponseEntity<>("Product is deleted successfully.", HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete product by ID")
    @DeleteMapping("/delete/id/{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable Integer productId) {
        try {
            productService.deleteProductByID(productId);
            return new ResponseEntity<>("Product is deleted successfully.", HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a review for a product")
    @PostMapping("/review/{email}/{productId}/{rating}")
    public String addProductReview(
            @PathVariable String email,
            @PathVariable int productId,
            @PathVariable double rating,
            @RequestBody String reviewText) {
        return productService.addProductReview(email, productId, rating, reviewText);
    }

    @Operation(summary = "Get all reviews for a product by ID")
    @GetMapping("/reviews/{productId}")
    public List<ProductReview> getReviews(@PathVariable int productId) {
        return productService.getReviewsByProductId(productId);
    }

    @Operation(summary = "Get all products sorted by rating (descending)")
    @GetMapping("/products/sorted-by-rating")
    public ResponseEntity<List<Product>> getProductsByRatingDesc() {
        return ResponseEntity.ok(productService.getProductsSortedByRating());
    }

    @Operation(summary = "Get paginated list of products")
    @GetMapping("/pages")
    public ResponseEntity<HashMap<String, Object>> paginatedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Product> productPage = productService.getPaginatedProducts(page, size);
        HashMap<String, Object> response = new HashMap<>();
        response.put("products", productPage.getContent());
        response.put("currentPage", productPage.getNumber());
        response.put("totalPages", productPage.getTotalPages());
        response.put("totalItems", productPage.getTotalElements());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get product details by product ID (for inter-service communication)")
    @GetMapping("/product/{id}")
    public ProductShare getDetailsByProductId(@PathVariable Integer id) {
        return productService.getDetailsByProductId(id);
    }

    @Operation(summary = "Update quantity of a product by ID")
    @GetMapping("/updateQuantity/{productId}/{quantity}")
    public void updateQuantity(@PathVariable Integer productId, @PathVariable Integer quantity) {
        productService.updateProductByQuantity(productId, quantity);
    }
}
