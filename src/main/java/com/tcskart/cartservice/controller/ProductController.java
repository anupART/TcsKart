package com.tcskart.cartservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcskart.cartservice.entity.Product;
import com.tcskart.cartservice.repository.ProductRepository;
import com.tcskart.cartservice.service.ProductServiceImpl;
import com.tcskart.cartservice.service.ProductsNotAvailabeService;
import com.tcskart.cartservice.util.ProductCategory;


@RestController
@RequestMapping("/api/v1.0")
public class ProductController {


	ProductServiceImpl productService;
	
	ProductRepository productRepository;
	
	ProductsNotAvailabeService productNotAvailabeService;
	
	public ProductController(ProductServiceImpl productService, ProductRepository productRepository, ProductsNotAvailabeService productNotAvailabeService) {
		this.productRepository = productRepository;
		this.productService = productService;
		this.productNotAvailabeService = productNotAvailabeService;
	}
	
	@GetMapping("/product")
	public List<Product> get() {
		return productRepository.findAll();
	}

	@PostMapping("/product")
	public Product addProduct(@RequestBody Product product) {
		return productRepository.save(product);
	}

	@GetMapping("/products/{productCategory}")
	public List<Product> searchByProductCategory(@PathVariable ProductCategory productCategory){
		return productService.searchByProductCategory(productCategory);
	}
	@DeleteMapping("/product/{productId}")
	public void deleteByProductId(@PathVariable Integer productId){
		 productService.deleteById(productId);
	}
	
	@PostMapping("/product/{pincode}/{productId}")
	public ResponseEntity<String> addProductToNotAvailable(@PathVariable Integer pincode,@PathVariable Integer productId) {
		return productNotAvailabeService.addProductToNotAvailable(pincode, productId);
	}
	
	@DeleteMapping("/product/{pincode}/{productId}")
	public ResponseEntity<String> makeProductAvailableForPincode(@PathVariable Integer pincode,@PathVariable Integer productId) {
		return productNotAvailabeService.makeProductAvailableForPincode(pincode, productId);
	}
}
