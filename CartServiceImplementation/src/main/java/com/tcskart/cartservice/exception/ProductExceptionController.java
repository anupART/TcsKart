package com.tcskart.cartservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionController {

	@ExceptionHandler(InValidProductIdException.class)
	public ResponseEntity<String> inValidException() {
		return new ResponseEntity<String>("Product ID can't be negative", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> productNotFound() {
		return new ResponseEntity<String>("Product Not Found", HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(AlreadyProductMyWishList.class)
	public ResponseEntity<String> Product() {
		return new ResponseEntity<String>("This Product Already in your Wishlist", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AlreadyProductNotAvailable.class)
	public ResponseEntity<String> alreadyProductNotAvailable() {
		return new ResponseEntity<String>("Already the product is unavailable for the pincode.", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AlreadyProductAvailable.class)
	public ResponseEntity<String> alreadyProductAvailable() {
		return new ResponseEntity<String>("Already the product is available for the pincode.", HttpStatus.NOT_FOUND);
	}
}
