package com.tcskart.cartservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcskart.cartservice.entity.Wishlist;

import jakarta.transaction.Transactional;

//@Repository
//public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
//
//	@Query("SELECT new Wishlist(wishlistId,user,product) From Wishlist w WHERE w.email =:userEmail")
//	List<Wishlist> findByUserEmail(String userEmail);
//	
//	@Transactional
//	@Modifying
//	@Query("DELETE FROM Wishlist w WHERE w.user.email =:userEmail AND w.productId = :productId")
//	void deleteByUserAndProduct(String userEmail, Integer productId);
//	
//	@Transactional
//	@Modifying
//	@Query("DELETE FROM Wishlist w WHERE w.user.email =:userEmail")
//	void deleteByUser(String userEmail);
//	
//	@Query("SELECT wishlistId FROM Wishlist w WHERE w.user.email =:userEmail AND w.product.productId = :productId")
//	Integer findByUserEmailAndProductId(String userEmail, Integer productId);
//	
//}
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    // 1. Use entity field names and return whole entities
    @Query("SELECT w FROM Wishlist w WHERE w.email = :userEmail")
    List<Wishlist> findByUserEmail(@Param("userEmail") String userEmail);

    // 2. Deletion by email and productId
    @Transactional
    @Modifying
    @Query("DELETE FROM Wishlist w WHERE w.email = :userEmail AND w.productId = :productId")
    void deleteByUserAndProduct(@Param("userEmail") String userEmail, @Param("productId") Integer productId);

    // 3. Deletion by email only
    @Transactional
    @Modifying
    @Query("DELETE FROM Wishlist w WHERE w.email = :userEmail")
    void deleteByUser(@Param("userEmail") String userEmail);

    // 4. Get wishlistId by email and productId
    @Query("SELECT w.wishlistId FROM Wishlist w WHERE w.email = :userEmail AND w.productId = :productId")
    Integer findByUserEmailAndProductId(@Param("userEmail") String userEmail, @Param("productId") Integer productId);
}

