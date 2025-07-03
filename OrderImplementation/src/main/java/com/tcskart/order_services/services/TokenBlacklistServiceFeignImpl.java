package com.tcskart.order_services.services;

import org.springframework.stereotype.Service;

import com.tcskart.order_services.feign.TokenBlacklistFeignClient;
import com.tcskart.security.TokenBlacklistService;

@Service
public class TokenBlacklistServiceFeignImpl implements TokenBlacklistService {

    private final TokenBlacklistFeignClient client;

    public TokenBlacklistServiceFeignImpl(TokenBlacklistFeignClient client) {
        this.client = client;
    }	

    @Override
    public boolean isTokenBlacklisted(String token) {
        return client.isTokenBlacklisted(token);
    }
}
