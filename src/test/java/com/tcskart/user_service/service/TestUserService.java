package com.tcskart.user_service.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tcskart.user_service.entity.User;
import com.tcskart.user_service.exception.EmailExistsException;
import com.tcskart.user_service.exception.InvalidEmailException;
import com.tcskart.user_service.exception.InvalidPhoneNumberException;
import com.tcskart.user_service.exception.PasswordException;
import com.tcskart.user_service.exception.PhoneNumberExistsException;
import com.tcskart.user_service.repo.UserRepo;
import com.tcskart.user_service.util.PasswordUtil;
import com.tcskart.user_service.util.Role;

@ExtendWith(MockitoExtension.class)
public class TestUserService {
	
	@Mock
	UserRepo repo;
	
	@Mock
	PasswordUtil util;
	
	@InjectMocks
	UserService service;
	
	@Test
	void testAddCustomer_success() {
		
		String name = "testUser";
		String email = "testuser123@gmail.com";
		long phoneNumber = 9876543210l;
		String password = "testPassword";
		String status = "ACTIVE";
		
		String encryptedPassword = "passwordEncrypted";
		
		when(util.encrypt(password)).thenReturn(encryptedPassword);
		
		User mockUser = new User(name, email, phoneNumber, password);
		mockUser.setPassword(util.encrypt(encryptedPassword));
		mockUser.setRole(Role.CUSTOMER);
		mockUser.setStatus(status);
		mockUser.setId(1);
		
		when(repo.save(Mockito.<User>any())).thenReturn(mockUser);
		
		mockUser.setPassword(password);
		
		User result = service.addCustomer(mockUser);
		
		verify(repo).save(Mockito.<User>any());
		assertNotNull(result);
	}
	
	@Test
	void testAddCustomer_failureForExistEmail() {
		
		String name = "testUser";
		String email = "testuser123@gmail.com";	//Email Already Registered
		long phoneNumber = 9876543210l;
		String password = "testPassword";
		
		User mockUser = new User(name, email, phoneNumber, password);
				
		when(repo.existsByEmail(email)).thenReturn(true);
		
		assertThrows(EmailExistsException.class, ()->{
			service.addCustomer(mockUser);
		});
		verify(repo,never()).save(Mockito.<User>any());
	}
	
	@Test
	void testAddCustomer_failureForExistPhoneNumber() {
		
		String name = "testUser";
		String email = "testuser123@gmail.com";
		long phoneNumber = 9876543210l;	//PhoneNumber Already Registered
		String password = "testPassword";
		
		User mockUser = new User(name, email, phoneNumber, password);
				
		when(repo.existsByPhoneNumber(phoneNumber)).thenReturn(true);
		
		assertThrows(PhoneNumberExistsException.class, ()->{
			service.addCustomer(mockUser);
		});
		verify(repo,never()).save(Mockito.<User>any());
	}
	
	@Test
	void testAddCustomer_failureForInvalidEmail() {
		
		String name = "testUser";
		String email = "12345";		//Invalid Email
		long phoneNumber = 9876543210l;
		String password = "testPassword";
		
		User mockUser = new User(name, email, phoneNumber, password);
					
		assertThrows(InvalidEmailException.class, ()->{
			service.addCustomer(mockUser);
		});
		verify(repo,never()).save(Mockito.<User>any());
	}
	
	@Test
	void testAddCustomer_failureForInvalidPhoneNumber() {
		
		String name = "testUser";
		String email = "testuser123@gmail.com";
		long phoneNumber = 5556543210l;		//Invalid PhoneNumber
		String password = "testPassword";
		
		User mockUser = new User(name, email, phoneNumber, password);
					
		assertThrows(InvalidPhoneNumberException.class, ()->{
			service.addCustomer(mockUser);
		});
		verify(repo,never()).save(Mockito.<User>any());
	}
	
	@Test
	void testAddCustomer_failureForInvalidPassword() {
		
		String name = "testUser";
		String email = "testuser123@gmail.com";
		long phoneNumber = 9876543210l;		
		String password = "test";	//Invalid Password
		
		User mockUser = new User(name, email, phoneNumber, password);
					
		assertThrows(PasswordException.class, ()->{
			service.addCustomer(mockUser);
		});
		verify(repo,never()).save(Mockito.<User>any());
	}
	
	

}
