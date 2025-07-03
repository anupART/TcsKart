package com.tcskart.order_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
	    "com.tcskart.order_services",
	    "com.tcskart.security"
	})
@EnableFeignClients(basePackages = "com.tcskart.order_services.feign")
@EnableDiscoveryClient
public class OrderImplementationApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderImplementationApplication.class, args);
	}

}
