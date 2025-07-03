package com.tcskart.user_service.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcskart.security.JwtUtil;
import com.tcskart.user_service.entity.BlacklistedTokens;
import com.tcskart.user_service.repo.BlacklistedTokenRepo;

@Service
public class TokenBlacklistService implements com.tcskart.security.TokenBlacklistService{

	@Autowired
	BlacklistedTokenRepo repo;
	
	@Autowired
	JwtUtil jwtUtil;
	
	public void logout(String token) {
        LocalDateTime expiry = jwtUtil.extractClaims(token)
                                      .getExpiration()
                                      .toInstant()
                                      .atZone(java.time.ZoneId.systemDefault())
                                      .toLocalDateTime();

        repo.save(new BlacklistedTokens(token, expiry));
    }

	@Override
    public boolean isTokenBlacklisted(String token) {
        return repo.existsByToken(token);
    }
}
