package com.tcskart.order_services.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcskart.order_services.bean.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
	User findByEmail(String  email);
}
