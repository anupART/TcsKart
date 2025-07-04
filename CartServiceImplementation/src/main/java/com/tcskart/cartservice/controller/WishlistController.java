package com.tcskart.cartservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tcskart.cartservice.entity.Wishlist;
import com.tcskart.cartservice.service.WishlistService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/customers")
@Tag(name = "Wishlist Controller", description = "Handles operations related to user's wishlist")
public class WishlistController {

    @Autowired
    WishlistService wishlistService;

    @Operation(summary = "Get all products in the user's wishlist")
    @GetMapping("/wishlist/{userEmail}")
    public List<Wishlist> viewProductsFromWishlist(@PathVariable String userEmail) {
        return wishlistService.viewProductsFromWishlist(userEmail);
    }

    @Operation(summary = "Add a product to the user's wishlist")
    @PostMapping("/wishlist/{userEmail}/{productId}")
    public Wishlist addProductToWishlist(@PathVariable String userEmail, @PathVariable Integer productId) {
        return wishlistService.addProductToWishlist(userEmail, productId);
    }

    @Operation(summary = "Remove a product from the user's wishlist")
    @DeleteMapping("/wishlist/{userEmail}/{productId}")
    public ResponseEntity<String> removeProductFromWishlist(@PathVariable String userEmail, @PathVariable Integer productId) {
        return wishlistService.removeProductFromWishlist(userEmail, productId);
    }

    @Operation(summary = "Remove all products from the user's wishlist")
    @DeleteMapping("/wishlist/{userEmail}")
    public ResponseEntity<String> removeProductFromWishlist(@PathVariable String userEmail) {
        return wishlistService.removeAllProductFromWishlist(userEmail);
    }
}
