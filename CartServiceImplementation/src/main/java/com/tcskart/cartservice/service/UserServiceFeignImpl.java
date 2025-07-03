package com.tcskart.cartservice.service;

import org.springframework.stereotype.Service;

import com.tcskart.cartservice.dto.UserDto;
import com.tcskart.cartservice.feign.UserServiceFeignClient;


@Service
public class UserServiceFeignImpl implements UserServiceFeignClient {

    private final UserServiceFeignClient client;

    public UserServiceFeignImpl(UserServiceFeignClient client) {
        this.client = client;
    }

	@Override
	public UserDto getUserDetails(String email) {
		return client.getUserDetails(email);
	}

    
}
