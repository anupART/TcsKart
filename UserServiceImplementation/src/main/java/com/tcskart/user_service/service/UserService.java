package com.tcskart.user_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.tcskart.security.JwtUtil;
import com.tcskart.user_service.dto.UserDto;
import com.tcskart.user_service.entity.User;
import com.tcskart.user_service.exception.EmailExistsException;
import com.tcskart.user_service.exception.InvalidCredentialsException;
import com.tcskart.user_service.exception.InvalidEmailException;
import com.tcskart.user_service.exception.InvalidPhoneNumberException;
import com.tcskart.user_service.exception.PasswordException;
import com.tcskart.user_service.exception.PhoneNumberExistsException;
import com.tcskart.user_service.repo.UserRepo;
import com.tcskart.user_service.security.PasswordUtil;
import com.tcskart.user_service.util.Role;
import com.tcskart.user_service.util.Validations;

@Service
public class UserService {

	@Autowired
	UserRepo repo;
	
	@Autowired
	PasswordUtil util;
	
	@Autowired
	Validations validation;
	
	@Autowired
	JwtUtil jwtUtil;
	
	
	public User addCustomer(User user) {
		if(repo.existsByEmail(user.getEmail())) {
			throw new EmailExistsException();
		}
		if(repo.existsByPhoneNumber(user.getPhoneNumber())) {
			throw new PhoneNumberExistsException();
		}
		if(!validation.emailValidation(user.getEmail())) {
			throw new InvalidEmailException();
		}
		if(!validation.phoneNumberValidation(user.getPhoneNumber())) {
			throw new InvalidPhoneNumberException();
		}
		if(validation.passwordValidation(user.getPassword())) {
			throw new PasswordException();
		}
		user.setRole(Role.CUSTOMER);
		user.setStatus("ACTIVE");
		user.setPassword(util.encrypt(user.getPassword()));
		return repo.save(user);
	}
	
	public String login(String email,String password) {
		String encryptedpassword = repo.getPasswordByEmail(email);
		boolean isMatches = util.matches(password, encryptedpassword);
		if(isMatches && email.equals("admin123@gmail.com")) {
			return jwtUtil.generateToken(email,"ADMIN");
		}
		else if(isMatches) {
			return jwtUtil.generateToken(email,"CUSTOMER");
		}
		else {
			throw new InvalidCredentialsException();
		}
	}
	
	
	
	public List<UserDto> viewAllCustomers(){
		List<User> users = (List<User>) repo.findAll();
		List<UserDto> usersDto = new ArrayList<>();
		for(User user:users) {
			usersDto.add(new UserDto(user.getName(), user.getEmail(), user.getPhoneNumber(), user.getRole(), user.getStatus()));
		}
		return usersDto;
	}
	
	public UserDto getUserDetails(String email) {
		User user = repo.findByEmail(email);
		return new UserDto(user.getName(), user.getEmail(), user.getPhoneNumber(), user.getRole(), user.getStatus());
	}

}
