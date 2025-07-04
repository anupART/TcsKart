package com.tcskart.user_service.entity;

import com.tcskart.user_service.util.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(
	name = "user",
	uniqueConstraints = @UniqueConstraint(columnNames = {
		"email", "phoneNumber"
	})
)
@Schema(description = "Represents a user entity in the system")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Auto-generated user ID", example = "1")
	private int id;
	
	@Column(nullable = false)
	@Schema(description = "Full name of the user", example = "John Doe")
	private String name;
	
	@Column(nullable = false)
	@Schema(description = "Email address of the user", example = "john.doe@example.com")
	private String email;
	
	@Column(nullable = false)
	@Schema(description = "Phone number of the user", example = "9876543210")
	private long phoneNumber;
	
	@Column(nullable = false)
	@Schema(description = "Password of the user", example = "password123")
	private String password;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@Schema(description = "Role of the user", example = "USER")
	private Role role;
	
	@Column(nullable = false)
	@Schema(description = "Account status (e.g., ACTIVE, INACTIVE)", example = "ACTIVE")
	private String status;
	
	public User() {}
	
	public User(int id, String name, String email, long phoneNumber, String password, Role role, String status) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.role = role;
		this.status = status;
	}
	
	public User(String name, String email, long phoneNumber, String password) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}
	
	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
