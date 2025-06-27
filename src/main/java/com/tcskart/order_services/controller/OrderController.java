package com.tcskart.order_services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.tcskart.order_services.services.OrderServices;

@RestController
public class OrderController {
	
	@Autowired
	OrderServices service;

}
