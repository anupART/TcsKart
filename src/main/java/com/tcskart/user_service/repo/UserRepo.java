package com.tcskart.user_service.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcskart.user_service.entity.User;

@Repository
public interface UserRepo extends CrudRepository<User, Integer>{
	
		boolean existsByEmail(String email);
		boolean existsByPhoneNumber(long phoneNumber);
		
		@Query(value="select password from user where email=:email",nativeQuery = true)
		String getPassword(String email);
}
