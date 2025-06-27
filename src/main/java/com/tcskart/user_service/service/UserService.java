package com.tcskart.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcskart.user_service.entity.User;
import com.tcskart.user_service.exception.EmailExistsException;
import com.tcskart.user_service.exception.InvalidEmailException;
import com.tcskart.user_service.exception.InvalidPhoneNumberException;
import com.tcskart.user_service.exception.PasswordException;
import com.tcskart.user_service.exception.PhoneNumberExistsException;
import com.tcskart.user_service.repo.UserRepo;
import com.tcskart.user_service.util.PasswordUtil;
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
