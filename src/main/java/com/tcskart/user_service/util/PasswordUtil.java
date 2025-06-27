package com.tcskart.user_service.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordUtil {
	
	private final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	
	public String encrypt(String rawPassword) {
		return ENCODER.encode(rawPassword);
	}
	
	public boolean matches(String rawPassword, String encryptedPassword) {
		return ENCODER.matches(rawPassword, encryptedPassword);
	}
}
