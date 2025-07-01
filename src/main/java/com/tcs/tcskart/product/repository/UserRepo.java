package com.tcs.tcskart.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.tcskart.product.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	User findByEmail(String email);
}
