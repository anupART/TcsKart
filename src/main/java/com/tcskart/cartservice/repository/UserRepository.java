package com.tcskart.cartservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcskart.cartservice.entity.User;


@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	
		boolean existsByEmail(String email);
		boolean existsByPhoneNumber(long phoneNumber);
		
		@Query(value="select password from user where email=:email",nativeQuery = true)
		String getPassword(String email);
		
		Optional<User> findByEmail(String userEmail);
}
