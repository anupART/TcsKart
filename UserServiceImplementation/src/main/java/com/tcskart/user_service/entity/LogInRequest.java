package com.tcskart.user_service.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents the login request containing user credentials")
public class LogInRequest {
	
	@Schema(description = "Email used for login", example = "user@example.com", required = true)
	private String email;

	@Schema(description = "Password used for login", example = "password123", required = true)
	private String password;
	
	public LogInRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public LogInRequest() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
