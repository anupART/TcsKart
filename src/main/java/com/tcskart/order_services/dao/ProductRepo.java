package com.tcskart.order_services.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcskart.order_services.bean.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>{

}
