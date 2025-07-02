package com.tcskart.cartservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcskart.cartservice.entity.Wishlist;
import com.tcskart.cartservice.service.WishlistService;

@RestController
@RequestMapping("/api/v1.0")
public class WishlistController {

	@Autowired
	WishlistService wishlistService;
	
	@GetMapping("/wishlist/{userEmail}")
	public List<Wishlist> viewProductsFromWishlist(@PathVariable String userEmail) {
		return wishlistService.viewProductsFromWishlist(userEmail);
	}
	
	@PostMapping("/wishlist/{userEmail}/{productId}")
	public Wishlist addProductToWishlist(@PathVariable String userEmail,@PathVariable Integer productId) {
		return wishlistService.addProductToWishlist(userEmail, productId);
	}
	
	@DeleteMapping("/wishlist/{userEmail}/{productId}")
	public ResponseEntity<String> removeProductFromWishlist(@PathVariable String userEmail,@PathVariable Integer productId){
		return wishlistService.removeProductFromWishlist(userEmail, productId);
	}

	@DeleteMapping("/wishlist/{userEmail}")
	public ResponseEntity<String> removeProductFromWishlist(@PathVariable String userEmail){
		return wishlistService.removeAllProductFromWishlist(userEmail);
	}
}
