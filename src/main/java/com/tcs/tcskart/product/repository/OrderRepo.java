package com.tcs.tcskart.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.tcskart.product.entity.Order;

@Repository
public interface OrderRepo  extends JpaRepository<Order, Integer>{
	List<Order> findByUserEmail(String email);
}
