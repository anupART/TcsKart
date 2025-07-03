package com.tcs.tcskart.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(scanBasePackages = {
	    "com.tcs.tcskart.product",
	    "com.tcskart.security"
	})
@EnableFeignClients(basePackages = "com.tcs.tcskart.product.feign")
@EnableDiscoveryClient
public class ProductserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductserviceApplication.class, args);
	}


}
