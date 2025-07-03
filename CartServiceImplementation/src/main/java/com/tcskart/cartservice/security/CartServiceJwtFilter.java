package com.tcskart.cartservice.security;

import org.springframework.stereotype.Component;

import com.tcskart.security.JwtAuthenticationFilter;
import com.tcskart.security.JwtUtil;
import com.tcskart.security.TokenBlacklistService;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class CartServiceJwtFilter extends JwtAuthenticationFilter {

    private final TokenBlacklistService tokenBlacklistService;

    public CartServiceJwtFilter(JwtUtil jwtUtil, TokenBlacklistService tokenBlacklistService) {
        super(jwtUtil);
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @Override
    protected String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader); // Debug

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            System.out.println("Extracted Token: " + token);

            if (tokenBlacklistService.isTokenBlacklisted(token)) {
                System.out.println("Token is blacklisted");
                return null;
            }

            return token;
        }

        System.out.println("No valid Authorization header found");
        return null;
    }

}
