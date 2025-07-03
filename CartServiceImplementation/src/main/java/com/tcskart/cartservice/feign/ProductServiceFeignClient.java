package com.tcskart.cartservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcskart.cartservice.dto.ProductShare;


@FeignClient(name = "productservice")
public interface ProductServiceFeignClient {

	@GetMapping("/products/product/{id}")
    ProductShare getDetailsByProductId(@PathVariable Integer id); 
	
	
}
