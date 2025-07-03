package com.tcskart.cartservice.dto;

import com.tcskart.cartservice.util.Role;

public class UserDto {

	private String name;
	
	private String email;
	
	private long phoneNumber;
	
	private Role role;
	
	private String status;

	public UserDto(String name, String email, long phoneNumber, Role role, String status) {
		super();
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.status = status;
	}

	public UserDto() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
