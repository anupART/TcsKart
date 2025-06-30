package com.tcs.tcskart.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.tcskart.product.entity.Product;
import com.tcs.tcskart.product.utility.ProductCategory;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
		
	 List<Product> findByProductCategory(ProductCategory productCategory);
}
