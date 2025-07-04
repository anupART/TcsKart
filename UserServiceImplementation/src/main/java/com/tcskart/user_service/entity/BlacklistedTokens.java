package com.tcskart.user_service.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Schema(description = "Represents a JWT token that has been blacklisted after logout or expiration")
public class BlacklistedTokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Auto-generated ID for the blacklisted token record", example = "101")
    private Long id;

    @Column(nullable = false, unique = true, length = 500)
    @Schema(description = "The JWT token that is blacklisted", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Column(nullable = false)
    @Schema(description = "The date and time when this token will expire", example = "2025-07-04T10:15:30")
    private LocalDateTime expiry;

    public BlacklistedTokens() {}

    public BlacklistedTokens(String token, LocalDateTime expiry) {
        this.token = token;
        this.expiry = expiry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }
}
