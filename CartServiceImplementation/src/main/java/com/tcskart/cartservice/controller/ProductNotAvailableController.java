package com.tcskart.cartservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcskart.cartservice.service.ProductsNotAvailabeService;


@RestController
public class ProductNotAvailableController {

	@Autowired
	ProductsNotAvailabeService productNotAvailabeService;
	
	@PostMapping("/product/{productId}/{pincode}")
	public ResponseEntity<String> addProductToNotAvailable(@PathVariable Integer productId,@PathVariable Integer pincode) {
		return productNotAvailabeService.addProductToNotAvailable(pincode, productId);
	}
	
	@DeleteMapping("/product/{pincode}/{productId}")
	public ResponseEntity<String> makeProductAvailableForPincode(@PathVariable Integer productId,@PathVariable Integer pincode) {
		return productNotAvailabeService.makeProductAvailableForPincode(pincode, productId);
	}
	
	@GetMapping("/product/{productId}/{pincode}")
	public boolean isProductNotAvailable(@PathVariable Integer productId,@PathVariable Integer pincode){
		return productNotAvailabeService.isProductNotAvailable(productId, pincode);
	}
}
