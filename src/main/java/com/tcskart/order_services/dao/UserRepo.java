package com.tcskart.order_services.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcskart.order_services.bean.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	User findByEmail(String  email);
}
