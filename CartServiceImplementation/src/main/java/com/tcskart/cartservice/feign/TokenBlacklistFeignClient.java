package com.tcskart.cartservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "UserServiceImplementation")
public interface TokenBlacklistFeignClient {

    @GetMapping("/tokens/blacklisted")
    boolean isTokenBlacklisted(@RequestParam("token") String token);
}
