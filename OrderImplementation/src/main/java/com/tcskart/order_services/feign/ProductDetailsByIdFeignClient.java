package com.tcskart.order_services.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcskart.order_services.dto.ProductShare;

@FeignClient(name = "productservice")
public interface ProductDetailsByIdFeignClient {

	@GetMapping("/products/product/{id}")
    ProductShare getDetailsByProductId(@PathVariable Integer id); 
    
    @GetMapping("/products/updateQuantity/{productId}/{quantity}")
    void updateQuantity(@PathVariable Integer productId, @PathVariable Integer quantity);
}


