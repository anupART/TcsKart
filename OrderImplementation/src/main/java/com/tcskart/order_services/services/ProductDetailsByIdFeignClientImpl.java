package com.tcskart.order_services.services;

import org.springframework.stereotype.Service;

import com.tcskart.order_services.dto.ProductShare;
import com.tcskart.order_services.feign.ProductDetailsByIdFeignClient;

@Service
public class ProductDetailsByIdFeignClientImpl implements ProductDetailsByIdFeignClient{

    private final ProductDetailsByIdFeignClient client;

    public ProductDetailsByIdFeignClientImpl(ProductDetailsByIdFeignClient client) {
        this.client = client;
    }

	@Override
	public ProductShare getDetailsByProductId(Integer id) {
		return client.getDetailsByProductId(id);
	}

	@Override
	public void updateQuantity(Integer productId, Integer quantity) {
		client.updateQuantity(productId, quantity);
	}
    
}
