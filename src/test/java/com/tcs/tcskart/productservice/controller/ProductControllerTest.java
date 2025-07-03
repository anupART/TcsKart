package com.tcs.tcskart.productservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.tcskart.product.ProductserviceApplication;
import com.tcs.tcskart.product.contoller.ProductController;

import com.tcs.tcskart.product.entity.Product;
import com.tcs.tcskart.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.Arrays;
import java.util.List;

@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = ProductserviceApplication.class) 
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

  @Test
    public void testAddProduct_Success() throws Exception {
        Product product = new Product();
        product.setProductName("Laptop");
        product.setDescription("Description");
        product.setProductPrice(100.0);
        product.setQuantity(10);

        when(productService.addProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Product added successfully."));
    }
 
    @Test
    public void testGetProductsByName_Success() throws Exception {
        String productName = "Laptop";
        
        Product product1 = new Product("Laptop", "Description1", 100.0, 10, null, 4.5);
        List<Product> products = Arrays.asList(product1);
        
        when(productService.viewProductsByName(productName)).thenReturn(products);
        
        mockMvc.perform(get("/api/products/name/{productName}", productName))
                .andExpect(status().isOk())  // Check for 200 OK
                .andExpect(jsonPath("$[0].productName").value("Laptop"))  
                .andExpect(jsonPath("$[0].description").value("Description1"));  
    }
    
    @Test
    public void testGetProductDetailsByName_Available() throws Exception {
        String productName = "Table";
        
        Product product1 = new Product("Table", "Description1", 100.0, 10, null, 4.5);
        List<Product> products = Arrays.asList(product1);
                when(productService.viewProductsByName(productName)).thenReturn(products);
        mockMvc.perform(get("/api/products/details/{productName}", productName))
                .andExpect(status().isOk())  
                .andExpect(jsonPath("$[0].productName").value("Table"))  
                .andExpect(jsonPath("$[0].availabilityStatus").value("Available")); 
    }


    @Test
    public void testGetProductDetailsByName_NotAvailable() throws Exception {
        String productName = "Shirt";
        
        Product product2 = new Product("Shirt", "Description2", 150.0, 0, null, 3.8);  
        List<Product> products = Arrays.asList(product2);
                when(productService.viewProductsByName(productName)).thenReturn(products);
        mockMvc.perform(get("/api/products/details/{productName}", productName))
                .andExpect(status().isOk())  
                .andExpect(jsonPath("$[0].productName").value("Shirt")) 
                .andExpect(jsonPath("$[0].availabilityStatus").value("Not Available"));
    }
    
    
    @Test
    public void testPaginatedProducts_Success() throws Exception {
        Product product1 = new Product("Pant", "Description1", 100.0, 10, null, 4.5);
        Product product2 = new Product("Jeans", "Description2", 150.0, 5, null, 4.2);
        List<Product> products = Arrays.asList(product1, product2);
        Page<Product> productPage = new PageImpl<>(products);
        when(productService.getPaginatedProducts(Mockito.anyInt(), Mockito.anyInt())).thenReturn(productPage);
        
        mockMvc.perform(get("/api/products/pages?page=0&size=2"))
                .andExpect(status().isOk())  
                .andExpect(jsonPath("$.products.size()").value(2)) 
                .andExpect(jsonPath("$.currentPage").value(0))
                .andExpect(jsonPath("$.totalPages").value(1))  
                .andExpect(jsonPath("$.totalItems").value(2)); 
    }


}