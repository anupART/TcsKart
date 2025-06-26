package com.tcs.tcskart.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tcs.tcskart.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
