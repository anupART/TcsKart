package com.tcs.tcskart.product.contoller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.tcskart.product.entity.Product;
import com.tcs.tcskart.product.repository.ProductRepository;
import com.tcs.tcskart.product.service.ProductServiceImpl;
import com.tcs.tcskart.product.utility.ProductCategory;


@RestController
@RequestMapping("/api/v1.0")
public class ProductController {


	ProductServiceImpl productService;
	
	ProductRepository productRepository;
	
	public ProductController(ProductServiceImpl productService, ProductRepository productRepository) {
		this.productRepository = productRepository;
		this.productService = productService;
	}
	
	@GetMapping("/product")
	public List<Product> get() {
		return productRepository.findAll();
	}

	@PostMapping("/product")
	public Product addProduct(@RequestBody Product product) {
//		product.setProductCategory(product.getProductCategory());
		
		System.out.println(product.toString());
		return productRepository.save(product);
	}

	@GetMapping("/products/{productCategory}")
	public List<Product> searchByProductCategory(@PathVariable ProductCategory productCategory){
		return productService.searchByProductCategory(productCategory);
	}
	
	@GetMapping("/product/{productId}")
	public Optional<Product> getProductById(@PathVariable Integer productId){
		 return productRepository.findById(productId);
	}
	@DeleteMapping("/product/{productId}")
	public void deleteByProductId(@PathVariable Integer productId){
		 productService.deleteById(productId);
	}
}
