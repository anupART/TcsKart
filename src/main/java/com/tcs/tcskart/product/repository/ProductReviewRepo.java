package com.tcs.tcskart.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.tcskart.product.entity.ProductReview;


public interface ProductReviewRepo extends JpaRepository<ProductReview, Long> {
    List<ProductReview> findByProductProductId(int productId);
}