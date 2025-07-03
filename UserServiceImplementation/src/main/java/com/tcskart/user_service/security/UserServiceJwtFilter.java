package com.tcskart.user_service.security;

import com.tcskart.security.JwtAuthenticationFilter;
import com.tcskart.security.JwtUtil;
import com.tcskart.user_service.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class UserServiceJwtFilter extends JwtAuthenticationFilter {

    private final TokenBlacklistService blacklistService;

    public UserServiceJwtFilter(JwtUtil jwtUtil, TokenBlacklistService blacklistService) {
        super(jwtUtil);
        this.blacklistService = blacklistService;
    }

    @Override
    protected String extractToken(HttpServletRequest request) {
        String token = super.extractToken(request);
        if (token != null && blacklistService.isTokenBlacklisted(token)) {
            return null;
        }
        return token;
    }
}
