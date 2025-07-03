package com.tcskart.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcskart.user_service.service.TokenBlacklistService;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @GetMapping("/blacklisted")
    public boolean isTokenBlacklisted(@RequestParam String token) {
        return tokenBlacklistService.isTokenBlacklisted(token);
    }
}