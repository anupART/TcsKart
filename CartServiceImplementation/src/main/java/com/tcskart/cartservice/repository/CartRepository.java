package com.tcskart.cartservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcskart.cartservice.entity.Cart;


@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

	Optional<Cart> findByEmail(String email);

}
