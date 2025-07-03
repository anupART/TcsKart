package com.tcskart.security;

public interface TokenBlacklistService {
    boolean isTokenBlacklisted(String token);
}