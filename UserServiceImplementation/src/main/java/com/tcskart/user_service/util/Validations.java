package com.tcskart.user_service.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class Validations {

	//UsingRFC5322Regex
	public boolean emailValidation(String email) {
	    String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
	    Pattern pattern = Pattern.compile(regexPattern);
	    Matcher matcher = pattern.matcher(email);
	    return matcher.matches();
	}
	
	public boolean phoneNumberValidation(long phoneNumber) {
		String number = String.valueOf(phoneNumber);
		String regexPattern = "^[6-9][0-9]{9}$";
		return number.matches(regexPattern);
	}
	
	public boolean passwordValidation(String password) {
		if(password.length()<8) {
			return true;
		}
		return false;
	}
}
