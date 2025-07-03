package com.tcskart.user_service.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcskart.user_service.entity.BlacklistedTokens;

@Repository
public interface BlacklistedTokenRepo extends CrudRepository<BlacklistedTokens, Long>{
	boolean existsByToken(String token);
}
