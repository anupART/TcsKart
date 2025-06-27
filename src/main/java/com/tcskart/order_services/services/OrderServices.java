package com.tcskart.order_services.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcskart.order_services.dao.OrderRepo;

@Service
public class OrderServices {
	
	@Autowired 
	OrderRepo repo;
}
