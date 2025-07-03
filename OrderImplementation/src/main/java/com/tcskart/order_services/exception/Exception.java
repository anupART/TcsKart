package com.tcskart.order_services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;




@ControllerAdvice
public class Exception {
	
	
	@ExceptionHandler(value = ProductNotFound.class)
	public ResponseEntity<Object> exception(ProductNotFound exception){
		
		return new ResponseEntity<>("Product is Not Found..!", HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(value = NotEnoughStock.class)
	public ResponseEntity<Object> exception(NotEnoughStock exception){
		
		return new ResponseEntity<>("Not Enough Stock!", HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(value = NoOrder.class)
	public ResponseEntity<Object> exception(NoOrder exception){
		
		return new ResponseEntity<>("No orders", HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(value = NoProductFound.class)
	public ResponseEntity<Object> exception(NoProductFound exception){
		
		return new ResponseEntity<>("No Product Found", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = ProductNotAvailable.class)
	public ResponseEntity<Object> exception(ProductNotAvailable exception){
		
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}

}
