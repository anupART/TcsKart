package com.tcs.tcskart.product.exception;

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
}
