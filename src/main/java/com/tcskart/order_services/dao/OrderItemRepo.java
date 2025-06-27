package com.tcskart.order_services.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcskart.order_services.bean.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{

}
