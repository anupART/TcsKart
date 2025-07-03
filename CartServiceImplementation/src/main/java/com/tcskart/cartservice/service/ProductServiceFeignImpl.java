package com.tcskart.cartservice.service;

import org.springframework.stereotype.Service;

import com.tcskart.cartservice.dto.ProductShare;
import com.tcskart.cartservice.feign.ProductServiceFeignClient;


@Service
public class ProductServiceFeignImpl implements ProductServiceFeignClient {

    private final ProductServiceFeignClient client;

    public ProductServiceFeignImpl(ProductServiceFeignClient client) {
        this.client = client;
    }

	@Override
	public ProductShare getDetailsByProductId(Integer id) {
		ProductShare detailsByProductId = client.getDetailsByProductId(id);
		//System.out.println(detailsByProductId.getProductId());
		return detailsByProductId;
	}

	

    
}
