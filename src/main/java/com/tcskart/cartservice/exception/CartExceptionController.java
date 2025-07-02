package com.tcskart.cartservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CartExceptionController {

	@ExceptionHandler(value = NoNegativeException.class)
	public ResponseEntity<String> noNegativeException(){
		return new ResponseEntity<>("ID can't be negative.", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<String> userNotFoundException(){
		return new ResponseEntity<>("User Not Found.", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = CartNotFoundException.class)
	public ResponseEntity<String> cartNotFoundException(){
		return new ResponseEntity<>("Cart Not Found for the User", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = NoItemsFoundInCartException.class)
	public ResponseEntity<String> noItemsFoundInCartException(){
		return new ResponseEntity<>("No Items avaiable in the cart.", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = NoEnoughStockException.class)
	public ResponseEntity<String> noEnoughStockException(){
		return new ResponseEntity<>("No enough itmes avaiable.", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = NoEnoughItemOfProductException.class)
	public ResponseEntity<String> noEnoughItemOfProductException(){
		return new ResponseEntity<>("Not enough items of the product in the cart.", HttpStatus.BAD_REQUEST);
	}
}
