package com.tcskart.order_services.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcskart.order_services.bean.CartItem;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Integer>{
    List<CartItem> findByCartId(int cartid);
}
