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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.tcskart.cartservice.entity.Cart;
import com.tcskart.cartservice.entity.CartItem;
import com.tcskart.cartservice.entity.Product;
import com.tcskart.cartservice.service.CartService;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    private Cart cart;
    private CartItem item;

    @BeforeEach
    void setup() {
        cart = new Cart();
        cart.setId(1);

        Product product = new Product();
        product.setProductId(1);
        product.setQuantity(10);

        item = new CartItem();
        item.setId(1);
        item.setQuantity(2);
        item.setProduct(product);
    }

    @Test
    void testGetAllItemsFromCart() throws Exception {
        List<CartItem> items = List.of(item);
        when(cartService.getAllItemsFromCart("a@gmail.com")).thenReturn(items);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/cart/a@gmail.com"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddProductToCart() throws Exception {
        when(cartService.addProductToCart("a@gmail.com", 1, 2)).thenReturn(cart);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/cart/a@gmail.com/1/2"))
                .andExpect(status().isOk());
    }

    @Test
    void testRemoveProductFromCartByQuantity() throws Exception {
        when(cartService.removeProductFromCartByQuantity("a@gmail.com", 1, 1))
                .thenReturn(ResponseEntity.ok("Removed 1 of products from cart"));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1.0/cart/a@gmail.com/1/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Removed 1 of products from cart"));
    }


    @Test
    void testAddProductFromCartByQuantity() throws Exception {
        when(cartService.addProductFromCartByQuantity("a@gmail.com", 1, 3))
                .thenReturn(ResponseEntity.ok("Increased 3 of products from cart"));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1.0/carts/a@gmail.com/1/3"))
                .andExpect(status().isOk())
                .andExpect(content().string("Increased 3 of products from cart"));
    }

    @Test
    void testRemoveAllItemsFromCart() throws Exception {
        when(cartService.removeAllItemsFromCart("a@gmail.com"))
                .thenReturn(ResponseEntity.ok("All Items from Cart are removed."));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1.0/cart/a@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("All Items from Cart are removed."));
    }

    @Test
    void testRemoveProductFromCart() throws Exception {
        when(cartService.removeProductFromCart("a@gmail.com", 1))
                .thenReturn(ResponseEntity.ok("Cart Item deleted successfully"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1.0/cart/a@gmail.com/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cart Item deleted successfully"));
    }
}
