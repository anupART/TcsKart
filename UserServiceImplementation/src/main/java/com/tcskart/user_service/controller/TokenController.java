package com.tcskart.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcskart.user_service.service.TokenBlacklistService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/tokens")
@Tag(name = "Token Controller", description = "Operations related to token blacklist management")
public class TokenController {

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Operation(summary = "Check if a given token is blacklisted")
    @GetMapping("/blacklisted")
    public boolean isTokenBlacklisted(@RequestParam String token) {
        return tokenBlacklistService.isTokenBlacklisted(token);
    }
}
