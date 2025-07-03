package com.tcskart.cartservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcskart.cartservice.dto.ProductShare;
import com.tcskart.cartservice.entity.ProductNotAvailableLocation;
import com.tcskart.cartservice.exception.AlreadyProductAvailable;
import com.tcskart.cartservice.exception.AlreadyProductNotAvailable;
import com.tcskart.cartservice.exception.ProductNotFoundException;
import com.tcskart.cartservice.repository.ProductsNotAvailabeRepository;

@Service
public class ProductsNotAvailabeService {

	ProductsNotAvailabeRepository productsNotAvailabeRepository;

	ProductServiceFeignImpl productService;


	public ProductsNotAvailabeService(ProductsNotAvailabeRepository productsNotAvailabeRepository,
			ProductServiceFeignImpl productService) {
		super();
		this.productsNotAvailabeRepository = productsNotAvailabeRepository;
		this.productService = productService;
	}

	public ResponseEntity<String> addProductToNotAvailable(Integer pincode, Integer productId) {

		Integer exists = productsNotAvailabeRepository.findByPincodeAndProductId(pincode, productId);

		if (exists != null) {
			throw new AlreadyProductNotAvailable();
		}
		ProductShare product = productService.getDetailsByProductId(productId);

		if(product == null) {
			throw new ProductNotFoundException();
		}
		ProductNotAvailableLocation productNotAvailable = new ProductNotAvailableLocation(pincode, product.getProductId());

		productsNotAvailabeRepository.save(productNotAvailable);

		return new ResponseEntity<>("Product made unavailable for " + pincode + " pincode", HttpStatus.OK);
	}

	public ResponseEntity<String> makeProductAvailableForPincode(Integer pincode, Integer productId) {

		Integer exists = productsNotAvailabeRepository.findByPincodeAndProductId(pincode, productId);

		if (exists == null) {
			throw new AlreadyProductAvailable();
		}
		ProductShare product = productService.getDetailsByProductId(productId);

		if(product == null) {
			throw new ProductNotFoundException();
		}

		ProductNotAvailableLocation productNotAvailable = new ProductNotAvailableLocation(pincode, product.getProductId());

		productsNotAvailabeRepository.deleteByPincodeAndProduct(pincode, productId);

		return new ResponseEntity<>("Product made available for " + pincode + " pincode", HttpStatus.OK);
	}

	public boolean isProductNotAvailable(Integer productId, Integer pincode) {
		return productsNotAvailabeRepository.existsByProductIdAndPincode(productId, pincode);
	}
}
