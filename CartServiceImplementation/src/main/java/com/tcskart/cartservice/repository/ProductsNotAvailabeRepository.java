package com.tcskart.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcskart.cartservice.entity.ProductNotAvailableLocation;

import jakarta.transaction.Transactional;

//@Repository
//public interface ProductsNotAvailabeRepository extends JpaRepository<ProductNotAvailableLocation, Integer> {
//
//	@Transactional
//	@Modifying
//	@Query("DELETE FROM ProductNotAvailableLocation w WHERE w.pincode =:pincode AND w.productId = :productId")
//	void deleteByPincodeAndProduct(Integer pincode, int productId);
//	
//	@Query("SELECT id FROM ProductNotAvailableLocation w WHERE w.pincode =:pincode AND w.productId = :productId")
//	Integer findByPincodeAndProductId(Integer pincode, Integer productId);
//
//	boolean existsByProduct_ProductIdAndPincode(int productId, int pincode);
//}
@Repository
public interface ProductsNotAvailabeRepository extends JpaRepository<ProductNotAvailableLocation, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductNotAvailableLocation w WHERE w.pincode = :pincode AND w.productId = :productId")
    void deleteByPincodeAndProduct(@Param("pincode") Integer pincode, @Param("productId") int productId);

    @Query("SELECT w.id FROM ProductNotAvailableLocation w WHERE w.pincode = :pincode AND w.productId = :productId")
    Integer findByPincodeAndProductId(@Param("pincode") Integer pincode, @Param("productId") Integer productId);

    boolean existsByProductIdAndPincode(int productId, int pincode);
}
