package com.tcskart.cartservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tcskart.cartservice.entity.Cart;
import com.tcskart.cartservice.entity.CartItem;
import com.tcskart.cartservice.service.CartService;

@RestController
@RequestMapping("/api/v1.0")
public class CartController {
	
	public CartController() {}
	
	CartService cartService;
	
	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@GetMapping("/cart/{userEmail}")
	List<CartItem> getAllItemsFromCart(@PathVariable String userEmail) {
		return cartService.getAllItemsFromCart(userEmail);
	}
	
	@PostMapping("/cart/{userEmail}/{productId}/{quantity}")
	Cart addProductsToCart(@PathVariable String userEmail,@PathVariable Integer productId, @PathVariable Integer quantity) {
		return cartService.addProductToCart(userEmail, productId, quantity);
	}
	
	@PatchMapping("/cart/{userEmail}/{productId}/{quantity}")
	ResponseEntity<String> removeProductFromCartByQuantity(@PathVariable String userEmail, @PathVariable Integer productId, @PathVariable Integer quantity) {
		return cartService.removeProductFromCartByQuantity(userEmail,productId,quantity);
	}
	
	@PatchMapping("/carts/{userEmail}/{productId}/{quantity}")
	ResponseEntity<String> addProductFromCartByQuantity(@PathVariable String userEmail, @PathVariable Integer productId, @PathVariable Integer quantity) {
		return cartService.addProductFromCartByQuantity(userEmail,productId,quantity);
	}
	
	@DeleteMapping("/cart/{userEmail}")
	ResponseEntity<String> removeAllItemsFromCart(@PathVariable String userEmail) {
		return cartService.removeAllItemsFromCart(userEmail);
	}
	
	@DeleteMapping("/cart/{userEmail}/{cartItemId}")
	ResponseEntity<String> removeProductFromCart(@PathVariable String userEmail, @PathVariable Integer cartItemId) {
		return cartService.removeProductFromCart(userEmail, cartItemId);
	}
}
