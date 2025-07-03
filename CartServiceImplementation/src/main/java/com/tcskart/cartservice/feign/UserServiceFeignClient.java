package com.tcskart.cartservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcskart.cartservice.dto.UserDto;


@FeignClient(name = "UserServiceImplementation")
public interface UserServiceFeignClient {

    @GetMapping("/users/email/{email}")
    UserDto getUserDetails(@PathVariable String email);
}
