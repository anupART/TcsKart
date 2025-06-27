package com.tcskart.order_services.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcskart.order_services.bean.Order;

@Repository
public interface OrderRepo  extends JpaRepository<Order, Integer>{
	List<Order> findByUserEmail(String email);

}
