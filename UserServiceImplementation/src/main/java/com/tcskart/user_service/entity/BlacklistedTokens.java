package com.tcskart.user_service.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BlacklistedTokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 500)
    private String token;

    @Column(nullable = false)
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

