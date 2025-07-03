package com.tcskart.user_service.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tcskart.user_service.entity.User;
import com.tcskart.user_service.exception.InvalidOTP;
import com.tcskart.user_service.exception.PasswordException;
import com.tcskart.user_service.repo.UserRepo;
import com.tcskart.user_service.security.PasswordUtil;
import com.tcskart.user_service.util.Validations;

@Service
public class ResetPasswordService {
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	UserRepo repo;
	
	@Autowired
	Validations validation;
	
	@Autowired
	PasswordUtil util;
	
	static String otp = null;
	
	public String getRandom() {
		Random r = new Random();
        int max=9999,min=1111;
        int randomNumber = (r.nextInt(max - min + 1) + min);
        return String.valueOf(randomNumber);
	}
	
	public String resetPasswordSend(String email) {
		otp = getRandom();
		String messageBody = "Dear User,\n\n"
		        + "We received a request to reset your password. Please use the following One-Time Password (OTP) to proceed:\n\n"
		        + "Your OTP is: " + otp + "\n\n"
		        + "This OTP is valid for the next 10 minutes. Do not share this code with anyone.\n\n"
		        + "If you did not request a password reset, please ignore this email.\n\n"
		        + "Regards,\n"
		        + "TCSKart Support Team";
		String subject = "OTP For Password Reset";
		
		if(repo.existsByEmail(email)) {
			try {
	
				SimpleMailMessage mailMessage = new SimpleMailMessage();
	
				mailMessage.setFrom("sdhanush0048@gmail.com");
				mailMessage.setTo(email);
				mailMessage.setText(messageBody);
				mailMessage.setSubject(subject);
	
				javaMailSender.send(mailMessage);
				return "Mail Sent Successfully...";
			}
	
			catch (Exception e) {
				return "Error while Sending Mail";
			}
		}
		else {
			throw new UsernameNotFoundException("Invalid Username");
		}
	}
	
	
	public String changePassword(String email,String OTP,String password) {
		if(!otp.equals(OTP)) {
			throw new InvalidOTP();
		}
		if(validation.passwordValidation(password)) {
			throw new PasswordException();
		}
		String newPassword = util.encrypt(password);
		User user = repo.findByEmail(email);
		user.setPassword(newPassword);
		repo.save(user);
		return "Password Updated Successfully";
	}
}
