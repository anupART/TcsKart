package com.tcskart.cartservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.tcskart.cartservice.entity.Cart;
import com.tcskart.cartservice.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

//	@Query("SELECT cartItem FROM CartItem cartItem WHERE cartItem.cart = :cart AND cartItem.product.id = :productId")
//	Optional<CartItem> findProductAndCart(Cart cart, Integer productId);
	@Query("SELECT c FROM CartItem c WHERE c.cart = :cart AND c.productId = :productId")
	Optional<CartItem> findProductAndCart(@Param("cart") Cart cart, @Param("productId") Integer productId);


	List<CartItem> findByCartId(Integer cartId);

	void deleteAllByCart(Cart cart);

	void deleteByCartId(Integer cartId);

}
