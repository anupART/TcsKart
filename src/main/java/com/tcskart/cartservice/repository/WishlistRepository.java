package com.tcskart.cartservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tcskart.cartservice.entity.Product;
import com.tcskart.cartservice.entity.Wishlist;

import jakarta.transaction.Transactional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

	@Query("SELECT new Wishlist(wishlistId,user,product) From Wishlist w WHERE w.user.email =:userEmail")
	List<Wishlist> findByUserEmail(String userEmail);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Wishlist w WHERE w.user.email =:userEmail AND w.product.productId = :productId")
	void deleteByUserAndProduct(String userEmail, Integer productId);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Wishlist w WHERE w.user.email =:userEmail")
	void deleteByUser(String userEmail);
	
	@Query("SELECT wishlistId FROM Wishlist w WHERE w.user.email =:userEmail AND w.product.productId = :productId")
	Integer findByUserEmailAndProductId(String userEmail, Integer productId);
	
}
