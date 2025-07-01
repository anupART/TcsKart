package com.tcs.tcskart.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.tcskart.product.entity.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{

}