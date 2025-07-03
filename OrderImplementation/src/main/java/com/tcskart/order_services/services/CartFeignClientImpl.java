package com.tcskart.order_services.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcskart.order_services.dto.CartItem;
import com.tcskart.order_services.feign.CartFeignClient;

@Service
public class CartFeignClientImpl implements CartFeignClient{

    private final CartFeignClient client;

    public CartFeignClientImpl(CartFeignClient client) {
        this.client = client;
    }

	@Override
	public List<CartItem> getAllItemItemsFromCart(String userEmail) {
		return client.getAllItemItemsFromCart(userEmail);
	}

	@Override
	public ResponseEntity<String> removeAllItemItemsFromCart(String userEmail) {
		return client.removeAllItemItemsFromCart(userEmail);
	}

	@Override
	public boolean isProductNotAvailable(Integer productId, Integer pincode) {
		return client.isProductNotAvailable(productId, pincode);
	}



    
}
