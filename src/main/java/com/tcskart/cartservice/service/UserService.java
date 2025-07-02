package com.tcskart.cartservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcskart.cartservice.entity.User;
import com.tcskart.cartservice.exception.EmailExistsException;
import com.tcskart.cartservice.exception.InvalidEmailException;
import com.tcskart.cartservice.exception.InvalidPhoneNumberException;
import com.tcskart.cartservice.exception.PasswordException;
import com.tcskart.cartservice.exception.PhoneNumberExistsException;
import com.tcskart.cartservice.repository.UserRepository;
import com.tcskart.cartservice.util.PasswordUtil;
import com.tcskart.cartservice.util.Role;
import com.tcskart.cartservice.util.Validations;

@Service
public class UserService {

	@Autowired
	UserRepository repo;
	
	@Autowired
	PasswordUtil util;
	
	@Autowired
	Validations validation;
	
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
	
	public boolean login(String email,String password) {
		String encryptedpassword = repo.getPassword(email);
		return util.matches(password, encryptedpassword);
	}
	

}
