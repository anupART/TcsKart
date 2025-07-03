package com.tcs.tcskart.productservice.service;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tcs.tcskart.product.entity.Product;
import com.tcs.tcskart.product.repository.ProductRepository;
import com.tcs.tcskart.product.service.ProductServiceImpl;
import com.tcs.tcskart.product.utility.ProductCategory;
import com.tcs.tcskart.product.utility.ProductNotFoundException;

import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)  
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository; 

    @InjectMocks
    private ProductServiceImpl productService;  

    @Test
    public void testAddProduct_success() {
        Product newProduct = new Product("Red Paper", "A4 Chart Paper", 25.0, 10, ProductCategory.HOME_APPLIANCES, null);
        when(productRepository.save(newProduct)).thenReturn(newProduct);
        Product result = productService.addProduct(newProduct);
        assertNotNull(result);
        assertEquals("Red Paper", result.getProductName());  
        assertEquals(0.0, result.getProductRating()); 
        verify(productRepository, times(1)).save(newProduct); 
    }

    

    @Test
    public void testAddProduct_existingProduct() {
        Product existingProduct = new Product("Red Paper", "A4 Chart Paper", 25.0, 10, ProductCategory.HOME_APPLIANCES, 4.5);
        when(productRepository.findByProductName(existingProduct.getProductName())).thenReturn(Arrays.asList(existingProduct));  
        Product result = productService.addProduct(existingProduct);
        assertNull(result);  
        verify(productRepository, times(0)).save(existingProduct);
    }
}
