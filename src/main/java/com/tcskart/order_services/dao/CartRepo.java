package com.tcskart.order_services.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcskart.order_services.bean.Cart;
import com.tcskart.order_services.bean.Order;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer>{
	Cart findByUserEmail(String email);
	Cart findByUserId(int id);

}
