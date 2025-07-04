package com.tcskart.user_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.tcskart.user_service.dto.UserDto;
import com.tcskart.user_service.entity.LogInRequest;
import com.tcskart.user_service.entity.User;
import com.tcskart.user_service.service.ResetPasswordService;
import com.tcskart.user_service.service.TokenBlacklistService;
import com.tcskart.user_service.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Handles registration, login, logout, password reset, and token operations")
public class UserController {
	
	@Autowired
	UserService service;
	
	@Autowired
	TokenBlacklistService tokenService;
	
	@Autowired
	ResetPasswordService passwordService;

	@Operation(summary = "Register a new customer")
	@PostMapping("/customers")
	void addCustomer(@RequestBody User user) {
		service.addCustomer(user);
	}

	@Operation(summary = "Register a new admin")
	@PostMapping("/admin")
	void addAdmin(@RequestBody User user) {
		service.addAdmin(user);
	}

	@Operation(summary = "Login and receive JWT token")
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LogInRequest request) {
		return ResponseEntity.ok(service.login(request.getEmail(), request.getPassword()));
	}

	@Operation(summary = "View all registered customers (Admin only)")
	@GetMapping("/admin/users")
	@PreAuthorize("hasRole('ADMIN')")
	public List<UserDto> viewAllCustomers() {
		return service.viewAllCustomers();	
	}

	@Operation(summary = "Logout and blacklist the current JWT token")
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = extractToken(request);
        if (token != null) {
            tokenService.logout(token);
            return ResponseEntity.ok("Logged out successfully.");
        }
        return ResponseEntity.badRequest().body("No token provided.");
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

	@Operation(summary = "Send password reset OTP to email")
    @GetMapping("/customers/reset/send/{email}")
    public String resetPasswordSend(@PathVariable String email) {
    	return passwordService.resetPasswordSend(email);
    }

	@Operation(summary = "Verify OTP and reset password")
    @GetMapping("/customers/reset/recieve/{email}/{otp}/{password}")
    public String verifyOtp(@PathVariable String email, @PathVariable String otp, @PathVariable String password) {
    	return passwordService.changePassword(email, otp, password);
    }

	@Operation(summary = "Check if a token is blacklisted")
    @GetMapping("/tokens/is-blacklisted")
    public boolean isTokenBlacklisted(@RequestParam String token) {
        return tokenService.isTokenBlacklisted(token);
    }

	@Operation(summary = "Get user details by email")
    @GetMapping("/email/{email}")
    public UserDto getUserDetails(@PathVariable String email) {
    	return service.getUserDetails(email);
    }
	
	@Operation(summary = "Update user details by email")
	@PutMapping("/customers/update/{email}/{name}/{phoneNumber}")
    public User updateProfileByEmail(@PathVariable String email, @PathVariable String name, @PathVariable long phoneNumber) {
        return service.updateProfileByEmail(email, name, phoneNumber);
    }
}
