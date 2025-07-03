package com.tcskart.order_services.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcskart.order_services.bean.Order;
import com.tcskart.order_services.dto.OrderDto;
import com.tcskart.order_services.dto.ProductSalesByUser;
import com.tcskart.order_services.dto.ProductSalesDTO;
import com.tcskart.order_services.services.OrderServices;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	OrderServices services;
	
	@PostMapping("/users/placeorder")
	public Order PlaceOrder(@RequestBody OrderDto orderdto)
	{
	      return  services.PlaceOrder(orderdto);	
	}
	@GetMapping("/users/myorders/{email}")
	 public List<Order> getOrdersByEmail(@PathVariable String email) {
	       return services.getOrdersByUserEmail(email);
	 }
	@GetMapping("/admins/allorders")
	 public List<Order> getAllOrders() {
	       return services.getallorders();
	       
	 }
	@GetMapping("/admins/updateorderstatus/{id}/{status}")
	public String changeStatus(@PathVariable int id,@PathVariable String status)
	{
		return services.updateStatus(id, status);
	}
	@GetMapping("/users/carttomoveorder/{email}/{address}")
	public Order carttoMoveOrder(@PathVariable String email,@PathVariable  String address)
	{
		return services.cartMoveToOrder(email, address);
	}
	
	@GetMapping("/admin/dashboard/ProductList")
	public List<ProductSalesDTO> highestSellingProduct()
	{
		return services.highestSellingProduct();
	}
	
	@GetMapping("/admin/dashboard/Top5")
	public List<ProductSalesDTO> highestSellingProductTop5()
	{
		return services.highestSellingProductTop5();
	}
	
	@GetMapping("/admin/dashboard/highSellingProductList")
	public List<ProductSalesByUser> highestSellingProductPerPerson()
	{
		//
		for(ProductSalesByUser a:services.highestSellingProductDistantUser())
		{
			System.out.println(a.getNoOfUser());
		}
		return services.highestSellingProductDistantUser();
	}
	
	@GetMapping("/users/email/{email}")
	 public List<Order> getOrdersSendByEmail(@PathVariable String email) {
	       return services.getOrdersByUserEmail(email);
	 }
//	
//	@GetMapping("/add/{productId}/{pincode}")
//	public ProductNotAvailableLocation addProduct(@PathVariable int productId,@PathVariable int pincode)
//	{
//		//System.out.println(productId);
//		return  services.addProduct(productId,pincode);
//	}

	
	 

}
