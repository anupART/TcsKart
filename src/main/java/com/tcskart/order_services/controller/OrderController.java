package com.tcskart.order_services.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tcskart.order_services.bean.Order;
import com.tcskart.order_services.bean.OrderItem;
import com.tcskart.order_services.bean.Product;
import com.tcskart.order_services.dto.OrderDto;
import com.tcskart.order_services.dto.OrderItemDto;
import com.tcskart.order_services.exception.ProductNotFound;
import com.tcskart.order_services.services.OrderServices;

@RestController
public class OrderController {
	
	@Autowired
	OrderServices services;
	
	@PostMapping("/placeorder")
	public Order PlaceOrder(@RequestBody OrderDto orderdto)
	{
		System.out.println(orderdto.getAddress());
	     
	      return  services.PlaceOrder(orderdto);
			
	}
	@GetMapping("/users/{email}")
	 public List<Order> getOrdersByEmail(@PathVariable String email) {
	       return services.getOrdersByUserEmail(email);
	 }
	@GetMapping("/admin/allorders")
	 public List<Order> getAllOrders() {
	       return services.getallorders();
	       
	 }
	@GetMapping("/admin/updateStatus/{id}/{status}")
	public String changeStatus(@PathVariable int id,@PathVariable String status)
	{
		return services.updateStatus(id, status);
	}
	 

}
