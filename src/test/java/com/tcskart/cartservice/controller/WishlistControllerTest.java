package com.tcskart.cartservice.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.tcskart.cartservice.entity.Product;
import com.tcskart.cartservice.entity.User;
import com.tcskart.cartservice.entity.Wishlist;
import com.tcskart.cartservice.service.WishlistService;

@WebMvcTest(WishlistController.class)
public class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishlistService wishlistService;

    private Wishlist wishlist;

    @BeforeEach
    void setUp() {
        wishlist = new Wishlist();
        wishlist.setWishlistId(1);

        // Setup dummy User and Product to avoid serialization issues
        User user = new User();
        user.setEmail("a@gmail.com");
        wishlist.setUser(user);

        Product product = new Product();
        product.setProductId(101);
        wishlist.setProduct(product);
    }

    @Test
    void testViewProductsFromWishlist() throws Exception {
        List<Wishlist> wishlistItems = List.of(wishlist);
        when(wishlistService.viewProductsFromWishlist("a@gmail.com")).thenReturn(wishlistItems);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/wishlist/a@gmail.com"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].wishlistId").value(1));
    }

    @Test
    void testAddProductToWishlist() throws Exception {
        when(wishlistService.addProductToWishlist("a@gmail.com", 101)).thenReturn(wishlist);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/wishlist/a@gmail.com/101"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wishlistId").value(1));
    }

    @Test
    void testRemoveProductFromWishlist() throws Exception {
        when(wishlistService.removeProductFromWishlist("a@gmail.com", 101))
                .thenReturn(ResponseEntity.ok("Product is successfully removed."));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1.0/wishlist/a@gmail.com/101"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string("Product is successfully removed."));
    }

    @Test
    void testRemoveAllProductFromWishlist() throws Exception {
        when(wishlistService.removeAllProductFromWishlist("a@gmail.com"))
                .thenReturn(ResponseEntity.ok("All Products are successfully removed."));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1.0/wishlist/a@gmail.com"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string("All Products are successfully removed."));
    }
}
