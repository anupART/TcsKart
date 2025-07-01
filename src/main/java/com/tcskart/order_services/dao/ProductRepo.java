package com.tcskart.order_services.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcskart.order_services.bean.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{

}
