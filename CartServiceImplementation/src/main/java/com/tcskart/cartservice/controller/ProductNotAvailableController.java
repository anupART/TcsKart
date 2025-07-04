package com.tcskart.cartservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tcskart.cartservice.service.ProductsNotAvailabeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/customers")
@Tag(name = "Product Not Available Controller", description = "Handles product availability for specific pincodes")
public class ProductNotAvailableController {

    @Autowired
    ProductsNotAvailabeService productNotAvailabeService;

    @Operation(summary = "Mark a product as not available for a specific pincode")
    @PostMapping("/product/{productId}/{pincode}")
    public ResponseEntity<String> addProductToNotAvailable(@PathVariable Integer productId, @PathVariable Integer pincode) {
        return productNotAvailabeService.addProductToNotAvailable(pincode, productId);
    }

    @Operation(summary = "Make a previously unavailable product available again for a specific pincode")
    @DeleteMapping("/product/{productId}/{pincode}")
    public ResponseEntity<String> makeProductAvailableForPincode(@PathVariable Integer productId, @PathVariable Integer pincode) {
        return productNotAvailabeService.makeProductAvailableForPincode(pincode, productId);
    }

    @Operation(summary = "Check if a product is marked as not available for a specific pincode")
    @GetMapping("/product/share/{productId}/{pincode}")
    public boolean isProductNotAvailable(@PathVariable Integer productId, @PathVariable Integer pincode) {
        return productNotAvailabeService.isProductNotAvailable(productId, pincode);
    }
}
