package com.tcskart.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {
	    "com.tcskart.user_service",
	    "com.tcskart.security"
	})
@EnableDiscoveryClient
public class UserServiceImplementationApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceImplementationApplication.class, args);
	}

}
