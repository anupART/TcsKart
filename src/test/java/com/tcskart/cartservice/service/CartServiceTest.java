package com.tcskart.cartservice.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tcskart.cartservice.entity.*;
import com.tcskart.cartservice.exception.*;
import com.tcskart.cartservice.repository.*;

public class CartServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartService cartService;

    private AutoCloseable closeable;

    private User mockUser;
    private Product mockProduct;
    private Cart mockCart;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setEmail("test@example.com");

        mockCart = new Cart();
        mockCart.setId(1);
        mockCart.setUser(mockUser);
        mockCart.setCartItems(new ArrayList<>());
        mockUser.setCart(mockCart);

        mockProduct = new Product();
        mockProduct.setProductId(1);
        mockProduct.setQuantity(10);
    }

    @Test
    void testAddProductToCart_Success() {
        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.of(mockUser));
        when(cartRepository.findByUser(mockUser)).thenReturn(Optional.of(mockCart));
        when(productRepository.findById(1)).thenReturn(Optional.of(mockProduct));
        when(productRepository.save(any())).thenReturn(mockProduct);
        when(cartRepository.save(any())).thenReturn(mockCart);

        Cart result = cartService.addProductToCart("a@gmail.com", 1, 2);

        assertEquals(1, result.getCartItems().size());
        assertEquals(8, mockProduct.getQuantity());
    }

    @Test
    void testAddProductToCart_NoUser() {
        when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> {
            cartService.addProductToCart("user@gmail.com", 1, 2);
        });
    }

    @Test
    void testGetAllItemsFromCart_Success() {
        CartItem item = new CartItem();
        item.setId(1);
        item.setQuantity(2);
        item.setProduct(mockProduct);
        mockCart.getCartItems().add(item);

        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.of(mockUser));
        when(cartRepository.findById(mockCart.getId())).thenReturn(Optional.of(mockCart));

        List<CartItem> items = cartService.getAllItemsFromCart("a@gmail.com");

        assertEquals(1, items.size());
    }

    @Test
    void testRemoveAllItemsFromCart_Success() {
        CartItem item = new CartItem();
        item.setQuantity(2);
        item.setProduct(mockProduct);

        List<CartItem> items = List.of(item);
        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.of(mockUser));
        when(cartItemRepository.findByCartId(1)).thenReturn(items);

        ResponseEntity<String> response = cartService.removeAllItemsFromCart("a@gmail.com");

        verify(cartItemRepository).deleteByCartId(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRemoveProductFromCartByQuantity_Success() {
        CartItem item = new CartItem();
        item.setId(1);
        item.setProduct(mockProduct);
        item.setQuantity(5);
        mockCart.getCartItems().add(item);

        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.of(mockUser));
        when(cartRepository.findById(1)).thenReturn(Optional.of(mockCart));
        when(productRepository.findById(1)).thenReturn(Optional.of(mockProduct));

        ResponseEntity<String> response = cartService.removeProductFromCartByQuantity("a@gmail.com", 1, 2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, item.getQuantity());
    }

    @Test
    void testAddProductFromCartByQuantity_Success() {
        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.of(mockUser));
        when(cartRepository.findById(1)).thenReturn(Optional.of(mockCart));
        when(productRepository.findById(1)).thenReturn(Optional.of(mockProduct));
        when(cartRepository.findByUser(mockUser)).thenReturn(Optional.of(mockCart));

        ResponseEntity<String> response = cartService.addProductFromCartByQuantity("a@gmail.com", 1, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRemoveProductFromCart_Success() {
        CartItem item = new CartItem();
        item.setId(1);
        item.setProduct(mockProduct);
        item.setQuantity(2);

        mockCart.getCartItems().add(item);

        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.of(mockUser));
        when(cartRepository.findById(1)).thenReturn(Optional.of(mockCart));

        ResponseEntity<String> response = cartService.removeProductFromCart("a@gmail.com", 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(cartItemRepository).deleteById(1);
    }

    @Test
    void testRemoveProductFromCart_CartItemNotFound() {
        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.of(mockUser));
        when(cartRepository.findById(1)).thenReturn(Optional.of(mockCart));

        ResponseEntity<String> response = cartService.removeProductFromCart("a@gmail.com", 999);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}

