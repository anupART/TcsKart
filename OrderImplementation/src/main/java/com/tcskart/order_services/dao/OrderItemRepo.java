package com.tcskart.order_services.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tcskart.order_services.bean.OrderItem;
import com.tcskart.order_services.dto.ProductSalesByUser;
import com.tcskart.order_services.dto.ProductSalesDTO;


//@Repository
//public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{
//	   @Query("SELECT new com.tcskart.order_services.dto.ProductSalesDTO(oi.productId, SUM(oi.quantity)) " +
//		       "FROM OrderItem oi GROUP BY oi.productId ORDER BY SUM(oi.quantity) DESC")
//		List<ProductSalesDTO>findTopSellingProductsWithCount();
//	    @Query("SELECT new com.tcskart.order_services.dto.ProductSalesDTO(oi.productId, SUM(oi.quantity)) " +
//	           "FROM OrderItem oi GROUP BY oi.productId ORDER BY SUM(oi.quantity) DESC")
//	    List<ProductSalesDTO> findTopSellingProductsWithCountTop(Pageable pageable);
//	    
//	    @Query("SELECT new com.tcskart.order_services.dto.ProductSalesByUser(oi.productId, COUNT(DISTINCT o.userEmail)) " +
//	    	       "FROM OrderItem oi JOIN oi.order o " +
//	    	       "GROUP BY oi.product " +
//	    	       "ORDER BY COUNT(DISTINCT o.userEmail) DESC")
//	    List<ProductSalesByUser> findTopSellingProductsByDistinctUsers(Pageable pageable);
//}
@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

    // Get all top selling products sorted by total quantity sold
    @Query("SELECT new com.tcskart.order_services.dto.ProductSalesDTO(oi.productId, SUM(oi.quantity)) " +
           "FROM OrderItem oi GROUP BY oi.productId ORDER BY SUM(oi.quantity) DESC")
    List<ProductSalesDTO> findTopSellingProductsWithCount();

    // Get top N selling products sorted by total quantity sold (paginated)
    @Query("SELECT new com.tcskart.order_services.dto.ProductSalesDTO(oi.productId, SUM(oi.quantity)) " +
           "FROM OrderItem oi GROUP BY oi.productId ORDER BY SUM(oi.quantity) DESC")
    List<ProductSalesDTO> findTopSellingProductsWithCountTop(Pageable pageable);

    // Get products sorted by number of **distinct users** who purchased them
    @Query("SELECT new com.tcskart.order_services.dto.ProductSalesByUser(oi.productId, COUNT(DISTINCT o.userEmail)) " +
           "FROM OrderItem oi JOIN oi.order o " +
           "GROUP BY oi.productId " +
           "ORDER BY COUNT(DISTINCT o.userEmail) DESC")
    List<ProductSalesByUser> findTopSellingProductsByDistinctUsers(Pageable pageable);
}
