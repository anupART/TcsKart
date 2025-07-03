package com.tcskart.user_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcskart.user_service.dto.UserDto;
import com.tcskart.user_service.entity.LogInRequest;
import com.tcskart.user_service.entity.User;
import com.tcskart.user_service.service.ResetPasswordService;
import com.tcskart.user_service.service.TokenBlacklistService;
import com.tcskart.user_service.service.UserService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService service;
	
	@Autowired
	TokenBlacklistService tokenService;
	
	@Autowired
	ResetPasswordService passwordService;
	
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
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LogInRequest request) {
		return ResponseEntity.ok(service.login(request.getEmail(),request.getPassword()));
	}
	
	
	@GetMapping("/admin/users")
	@PreAuthorize("hasRole('ADMIN')")
	public List<UserDto> viewAllCustomers(){
		return service.viewAllCustomers();	
	}
	
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = extractToken(request);
        if (token != null) {
            tokenService.logout(token);
            return ResponseEntity.ok("Logged out successfully.");
        }
        return ResponseEntity.badRequest().body("No token provided.");
    }

    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
	
    @GetMapping("/customers/reset/send/{email}")
    public String resetPasswordSend(@PathVariable String email) {
    	return passwordService.resetPasswordSend(email);
    }
    
    @GetMapping("/customers/reset/recieve/{email}/{otp}/{password}")
    public String verifyOtp(@PathVariable String email,@PathVariable String otp,@PathVariable String password) {
    	return passwordService.changePassword(email, otp, password);
    	
    }
    
    @GetMapping("/tokens/is-blacklisted")
    public boolean isTokenBlacklisted(@RequestParam String token) {
        return tokenService.isTokenBlacklisted(token);
    }
    
    @GetMapping("/email/{email}")
    public UserDto getUserDetails(@PathVariable String email) {
    	return service.getUserDetails(email);
    }
	
}
