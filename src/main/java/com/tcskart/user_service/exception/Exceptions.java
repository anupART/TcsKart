package com.tcskart.user_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Exceptions {
	
	@ExceptionHandler(value= EmailExistsException.class)
	public ResponseEntity<Object> emailExists()
	{
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Id Already Exits");
	}
	
	@ExceptionHandler(value = PhoneNumberExistsException.class)
	public ResponseEntity<Object> phoneNumberExists(){
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Entered PhoneNumber is Already Registered");
	}
	
	@ExceptionHandler(value = InvalidEmailException.class)
	public ResponseEntity<Object> invalidEmail(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You have entered Invalid Email");
	}
	
	@ExceptionHandler(value = InvalidPhoneNumberException.class)
	public ResponseEntity<Object> invalidphoneNumber(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You have entered Invalid PhoneNumber");
	}
	
	@ExceptionHandler(value = PasswordException.class)
	public ResponseEntity<Object> invalidPassword(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password length must be atleast 8 characters.");
	}
}
