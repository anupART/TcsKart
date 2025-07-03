package com.tcs.tcskart.product.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcs.tcskart.product.dto.Order;



@FeignClient(name = "OrderImplementation")
public interface OrderFeignClient {
	
	 @GetMapping("/orders/users/email{email}")
	 public List<Order> getOrdersSendByEmail(@PathVariable String email);

   
}
