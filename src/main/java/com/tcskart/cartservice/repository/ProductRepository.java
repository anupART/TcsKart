package com.tcskart.cartservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcskart.cartservice.entity.Product;
import com.tcskart.cartservice.util.ProductCategory;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
		
	 List<Product> findByProductCategory(ProductCategory productCategory);

}
