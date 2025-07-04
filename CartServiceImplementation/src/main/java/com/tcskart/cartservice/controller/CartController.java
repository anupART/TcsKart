package com.tcskart.cartservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tcskart.cartservice.entity.Cart;
import com.tcskart.cartservice.entity.CartItem;
import com.tcskart.cartservice.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/customers")
@Tag(name = "Cart Controller", description = "Handles cart-related operations for users")
public class CartController {

    public CartController() {}

    CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "Get all items in a user's cart")
    @GetMapping("/cart/{userEmail}")
    public List<CartItem> getAllItemsFromCart(@PathVariable String userEmail) {
        return cartService.getAllItemsFromCart(userEmail);
    }

    @Operation(summary = "Add a product to the user's cart")
    @PostMapping("/cart/{userEmail}/{productId}/{quantity}")
    public Cart addProductsToCart(@PathVariable String userEmail, @PathVariable Integer productId, @PathVariable Integer quantity) {
        return cartService.addProductToCart(userEmail, productId, quantity);
    }

    @Operation(summary = "Remove a specific quantity of a product from the cart")
    @PatchMapping("/cart/{userEmail}/{productId}/{quantity}")
    public ResponseEntity<String> removeProductFromCartByQuantity(@PathVariable String userEmail, @PathVariable Integer productId, @PathVariable Integer quantity) {
        return cartService.removeProductFromCartByQuantity(userEmail, productId, quantity);
    }

    @Operation(summary = "Increase product quantity in the cart")
    @PatchMapping("/carts/{userEmail}/{productId}/{quantity}")
    public ResponseEntity<String> addProductFromCartByQuantity(@PathVariable String userEmail, @PathVariable Integer productId, @PathVariable Integer quantity) {
        return cartService.addProductFromCartByQuantity(userEmail, productId, quantity);
    }

    @Operation(summary = "Remove all items from the user's cart")
    @DeleteMapping("/cart/{userEmail}")
    public ResponseEntity<String> removeAllItemsFromCart(@PathVariable String userEmail) {
        return cartService.removeAllItemsFromCart(userEmail);
    }

    @Operation(summary = "Remove a specific product from the user's cart")
    @DeleteMapping("/cart/{userEmail}/{cartItemId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable String userEmail, @PathVariable Integer cartItemId) {
        return cartService.removeProductFromCart(userEmail, cartItemId);
    }

    @Operation(summary = "Get all cart items via Feign client (for internal use)")
    @GetMapping("/feign/cart/{userEmail}")
    public List<CartItem> getAllItemItemsFromCart(@PathVariable String userEmail) {
        return cartService.getAllItemsFromCart(userEmail);
    }

    @Operation(summary = "Remove all cart items via Feign client (for internal use)")
    @DeleteMapping("/feign/cart/{userEmail}")
    public ResponseEntity<String> removeAllItemItemsFromCart(@PathVariable String userEmail) {
        return cartService.removeAllItemsFromCart(userEmail);
    }
}
