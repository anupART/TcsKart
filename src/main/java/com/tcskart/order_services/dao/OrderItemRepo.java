package com.tcskart.order_services.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tcskart.order_services.bean.OrderItem;
import com.tcskart.order_services.dto.ProductSalesByUser;
import com.tcskart.order_services.dto.ProductSalesDTO;


@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{
	   @Query("SELECT new com.tcskart.order_services.dto.ProductSalesDTO(oi.product, SUM(oi.quantity)) " +
		       "FROM OrderItem oi GROUP BY oi.product ORDER BY SUM(oi.quantity) DESC")
		List<ProductSalesDTO>findTopSellingProductsWithCount();
	    @Query("SELECT new com.tcskart.order_services.dto.ProductSalesDTO(oi.product, SUM(oi.quantity)) " +
	           "FROM OrderItem oi GROUP BY oi.product ORDER BY SUM(oi.quantity) DESC")
	    List<ProductSalesDTO> findTopSellingProductsWithCountTop(Pageable pageable);
	    
	    @Query("SELECT new com.tcskart.order_services.dto.ProductSalesByUser(oi.product, COUNT(DISTINCT o.user)) " +
	    	       "FROM OrderItem oi JOIN oi.order o " +
	    	       "GROUP BY oi.product " +
	    	       "ORDER BY COUNT(DISTINCT o.user) DESC")
	    List<ProductSalesByUser> findTopSellingProductsByDistinctUsers(Pageable pageable);
}
