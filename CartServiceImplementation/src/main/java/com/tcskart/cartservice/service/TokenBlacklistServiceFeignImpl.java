package com.tcskart.cartservice.service;

import com.tcskart.security.TokenBlacklistService;
import com.tcskart.cartservice.feign.TokenBlacklistFeignClient;
import org.springframework.stereotype.Service;

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
