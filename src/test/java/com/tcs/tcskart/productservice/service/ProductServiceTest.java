package com.tcs.tcskart.productservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.tcs.tcskart.product.entity.Product;
import com.tcs.tcskart.product.exception.ProductNotFoundException;
import com.tcs.tcskart.product.repository.ProductRepository;
import com.tcs.tcskart.product.service.ProductServiceImpl;
import com.tcs.tcskart.product.utility.ProductCategory;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@Mock
	ProductRepository productRepository;
	
	@InjectMocks
	ProductServiceImpl productServiceImpl;
	
	@Test
	void testProductDeleteById_WhenProductExists() {
		Product product = new Product("Pen", "Writing", 10.0, 10, ProductCategory.STATIONERY, 4.5);
		product.setProductId(10);
		
		when(productRepository.findById(10)).thenReturn(Optional.of(product));
		
		ResponseEntity<String> deletedProduct = productServiceImpl.deleteById(10);
		assertEquals("Product Deletion Successfull.", deletedProduct.getBody());
		assertEquals(HttpStatus.OK, deletedProduct.getStatusCode());
	}
	
//	@Test
//	void testProductDeleteById_WhenProductNotExists() {
//		assertThrows(ProductNotFoundException.class, productServiceImpl.deleteById(10));
//	}
}
