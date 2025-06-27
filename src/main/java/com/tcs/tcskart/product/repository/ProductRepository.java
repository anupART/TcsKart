package com.tcs.tcskart.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tcs.tcskart.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	 List<Product> findByProductName(String productName);

	List<Product> findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String keyword,
			String keyword2);
}
