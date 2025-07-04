package com.tcskart.order_services.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcskart.order_services.dto.CartItem;

@FeignClient(name = "cart-service")
public interface CartFeignClient {
    
    
    @GetMapping("/customers/feign/cart/{userEmail}")
	List<CartItem> getAllItemItemsFromCart(@PathVariable String userEmail);
    
    @DeleteMapping("/customers/feign/cart/{userEmail}")
	ResponseEntity<String> removeAllItemItemsFromCart(@PathVariable String userEmail);
    
    @GetMapping("/customers/product/share/{productId}/{pincode}")
	public boolean isProductNotAvailable(@PathVariable Integer productId,@PathVariable Integer pincode);
    
}


