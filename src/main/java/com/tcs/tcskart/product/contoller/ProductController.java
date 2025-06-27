package com.tcs.tcskart.product.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tcs.tcskart.product.entity.Product;
import com.tcs.tcskart.product.service.ProductService;
import com.tcs.tcskart.product.utility.ProductNotFoundException;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        Product addedProduct = productService.addProduct(product);

        if (addedProduct == null) {
            return new ResponseEntity<>("Product" + product.getProductName() + " already exists ", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Product added successfully.", HttpStatus.CREATED);
    }

    
    @GetMapping("/allproducts")
    public List<Product> getAllProducts() {
        return productService.viewAllProducts();
    }
    
    @GetMapping("/name/{productName}")
    public ResponseEntity<Object> getProductsByName(@PathVariable String productName) {
        try {
            List<Product> products = productService.viewProductsByName(productName);
            return new ResponseEntity<>(products, HttpStatus.OK); 
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



}


