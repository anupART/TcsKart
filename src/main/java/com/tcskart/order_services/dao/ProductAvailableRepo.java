package com.tcskart.order_services.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcskart.order_services.bean.ProductNotAvailableLocation;



@Repository
public interface ProductAvailableRepo extends JpaRepository<ProductNotAvailableLocation, Integer>{
	
	boolean existsByProduct_ProductIdAndPincode(int productId, int pincode);
}
