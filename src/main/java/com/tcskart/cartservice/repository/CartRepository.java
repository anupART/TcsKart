package com.tcskart.cartservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcskart.cartservice.entity.Cart;
import com.tcskart.cartservice.entity.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

	Optional<Cart> findByUser(User user);

	Optional<User> findByUserId(Integer userId);

}
