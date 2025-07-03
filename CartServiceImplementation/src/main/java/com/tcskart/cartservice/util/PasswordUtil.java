package com.tcskart.cartservice.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordUtil {
	
	private final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();
	
	public String encrypt(String rawPassword) {
		return ENCODER.encode(rawPassword);
	}
	
	public boolean matches(String rawPassword, String encryptedPassword) {
		return ENCODER.matches(rawPassword, encryptedPassword);
	}
}
