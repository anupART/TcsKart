package com.tcskart.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcskart.user_service.entity.User;
import com.tcskart.user_service.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService service;
	
	@PostMapping("/customers")
	void addCustomer(@RequestBody User user) {
		/*
		 * Name
		 * Email
		 * Phone no
		 * Password
		 */
		service.addCustomer(user);
	}
	
	@GetMapping("/customers/{email}/{password}")
	public String login(@PathVariable String email,@PathVariable String password) {
		if(service.login(email, password)) {
			return "Welcome You are Logged In";
		}
		return "Invalid Credentials";
	}
	
	
	
	
}
