package com.tcs.tcskart.product.service;

import com.tcskart.security.TokenBlacklistService;
import com.tcs.tcskart.product.feign.TokenBlacklistFeignClient;
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
