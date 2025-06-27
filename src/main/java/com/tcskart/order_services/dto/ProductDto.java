package com.tcskart.order_services.dto;

import com.tcskart.order_services.utlity.ProductCategory;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ProductDto {

	private Integer productId;


	private String productName;


	private String description;


	private Double productPrice;


	private Integer quantity;

	private ProductCategory productCategory;

	private Double productRating;

}
