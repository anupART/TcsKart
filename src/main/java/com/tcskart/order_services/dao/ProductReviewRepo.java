package com.tcskart.order_services.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcskart.order_services.bean.ProductReview;

@Repository
public interface ProductReviewRepo extends JpaRepository<ProductReview, Long> {
	    List<ProductReview> findByProductProductId(int productId);
}

