package com.tcs.tcskart.product.service;

import com.tcskart.security.TokenBlacklistService;
import com.tcs.tcskart.product.dto.Order;
import com.tcs.tcskart.product.feign.OrderFeignClient;
import com.tcs.tcskart.product.feign.TokenBlacklistFeignClient;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class OrderFeignImpl implements OrderFeignClient {

    private final OrderFeignClient client;

    public OrderFeignImpl(OrderFeignClient client) {
        this.client = client;
    }

 

	@Override
	public List<Order> getOrdersSendByEmail(String email) {
		return client.getOrdersSendByEmail(email);
	}
}
