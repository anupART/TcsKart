package com.tcskart.cartservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcskart.cartservice.entity.Product;
import com.tcskart.cartservice.entity.ProductNotAvailableLocation;
import com.tcskart.cartservice.exception.AlreadyProductAvailable;
import com.tcskart.cartservice.exception.AlreadyProductNotAvailable;
import com.tcskart.cartservice.exception.ProductNotFoundException;
import com.tcskart.cartservice.repository.ProductRepository;
import com.tcskart.cartservice.repository.ProductsNotAvailabeRepository;

@Service
public class ProductsNotAvailabeService {

	ProductsNotAvailabeRepository productsNotAvailabeRepository;

	ProductRepository productRepository;

	public ProductsNotAvailabeService(ProductsNotAvailabeRepository productsNotAvailabeRepository,
			ProductRepository productRepository) {
		this.productRepository = productRepository;
		this.productsNotAvailabeRepository = productsNotAvailabeRepository;
	}

	public ResponseEntity<String> addProductToNotAvailable(Integer pincode, Integer productId) {

		Integer exists = productsNotAvailabeRepository.findByPincodeAndProductId(pincode, productId);

		if (exists != null) {
			throw new AlreadyProductNotAvailable();
		}
		Optional<Product> product = productRepository.findById(productId);

		product.orElseThrow(() -> new ProductNotFoundException());

		ProductNotAvailableLocation productNotAvailable = new ProductNotAvailableLocation(pincode, product.get());

		productsNotAvailabeRepository.save(productNotAvailable);

		return new ResponseEntity<>("Product made unavailable for " + pincode + " pincode", HttpStatus.OK);
	}

	public ResponseEntity<String> makeProductAvailableForPincode(Integer pincode, Integer productId) {

		Integer exists = productsNotAvailabeRepository.findByPincodeAndProductId(pincode, productId);

		if (exists == null) {
			throw new AlreadyProductAvailable();
		}
		
		Optional<Product> product = productRepository.findById(productId);

		product.orElseThrow(() -> new ProductNotFoundException());

		ProductNotAvailableLocation productNotAvailable = new ProductNotAvailableLocation(pincode, product.get());

		productsNotAvailabeRepository.deleteByPincodeAndProduct(pincode, productId);

		return new ResponseEntity<>("Product made available for " + pincode + " pincode", HttpStatus.OK);
	}

}
