package com.tcskart.user_service.entity;

public class LogInRequest {
	
	private String email;
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
