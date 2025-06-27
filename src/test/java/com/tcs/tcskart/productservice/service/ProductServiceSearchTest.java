package com.tcs.tcskart.productservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tcs.tcskart.product.entity.Product;
import com.tcs.tcskart.product.repository.ProductRepository;
import com.tcs.tcskart.product.service.ProductServiceImpl;
import com.tcs.tcskart.product.utility.ProductNotFoundException;

@ExtendWith(MockitoExtension.class)  

public class ProductServiceSearchTest {

	 @Mock
	    private ProductRepository productRepository; 

	    @InjectMocks
	    private ProductServiceImpl productService;  
	    
	    
	    @Test
	    void viewProductsByProductFoundTest() {
	        Product product = new Product();
	        product.setProductId(1);
	        product.setProductName("TcsProduct");
	        product.setProductPrice(100.0);
	        product.setProductRating(4.5);
	        List<Product> mockProducts = new ArrayList<>();
	        mockProducts.add(product);
	        when(productRepository.findByProductName("TcsProduct")).thenReturn(mockProducts);
	        List<Product> products = productService.viewProductsByName("TcsProduct");
	        assertNotNull(products);
	        assertEquals(1, products.size());
	        assertEquals("TcsProduct", products.get(0).getProductName());
	        assertEquals(4.5, products.get(0).getProductRating());
	    }
	    
	    @Test
	    void viewProductsByProductNotFoundTest() {
	        String productName = "TcsNoProduct";
	        when(productRepository.findByProductName(productName)).thenReturn(Collections.emptyList());
	        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
	            productService.viewProductsByName(productName);
	        });
	        equals(exception.getMessage());
	    }
	    
	    @Test
	    void viewAllProductsTest() {
	        Product product1 = new Product();
	        product1.setProductId(1);
	        product1.setProductName("Product1");
	        product1.setProductPrice(100.0);
	        product1.setProductRating(4.0);
	        Product product2 = new Product();
	        product2.setProductId(2);
	        product2.setProductName("Product2");
	        product2.setProductPrice(200.0);
	        product2.setProductRating(5.0);
	        List<Product> mockProductList = Arrays.asList(product1, product2);
	        when(productRepository.findAll()).thenReturn(mockProductList);
	        List<Product> products = productService.viewAllProducts();
	        verify(productRepository).findAll();
	        assertNotNull(products);  
	        assertEquals(2, products.size());  
	        assertEquals("Product1", products.get(0).getProductName());
	        assertEquals(100.0, products.get(0).getProductPrice()); 
	        assertEquals("Product2", products.get(1).getProductName()); 
	        assertEquals(200.0, products.get(1).getProductPrice()); 
	    }


	    
	    

}


