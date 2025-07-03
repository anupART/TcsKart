package com.tcskart.cartservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import com.tcskart.cartservice.entity.Product;
import com.tcskart.cartservice.entity.User;
import com.tcskart.cartservice.entity.Wishlist;
import com.tcskart.cartservice.exception.*;
import com.tcskart.cartservice.repository.*;

class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private WishlistService wishlistService;

    private AutoCloseable closeable;

    private User mockUser;
    private Product mockProduct;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setEmail("a@gmail.com");

        mockProduct = new Product();
        mockProduct.setProductId(101);
    }

    @Test
    void testAddProductToWishlist_Success() {
        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.of(mockUser));
        when(productRepository.findById(101)).thenReturn(Optional.of(mockProduct));
        when(wishlistRepository.findByUserEmailAndProductId("a@gmail.com", 101)).thenReturn(null);

        Wishlist wishlist = new Wishlist(mockUser, mockProduct);
        when(wishlistRepository.save(any())).thenReturn(wishlist);

        Wishlist result = wishlistService.addProductToWishlist("a@gmail.com", 101);

        assertNotNull(result);
        assertEquals(mockUser, result.getUser());
        assertEquals(mockProduct, result.getProduct());
    }

    @Test
    void testAddProductToWishlist_AlreadyExists() {
        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.of(mockUser));
        when(productRepository.findById(101)).thenReturn(Optional.of(mockProduct));
        when(wishlistRepository.findByUserEmailAndProductId("a@gmail.com", 101)).thenReturn(1);

        assertThrows(AlreadyProductMyWishList.class, () ->
                wishlistService.addProductToWishlist("a@gmail.com", 101));
    }

    @Test
    void testAddProductToWishlist_UserNotFound() {
        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () ->
                wishlistService.addProductToWishlist("a@gmail.com", 101));
    }

    @Test
    void testAddProductToWishlist_ProductNotFound() {
        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.of(mockUser));
        when(productRepository.findById(101)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () ->
                wishlistService.addProductToWishlist("a@gmail.com", 101));
    }

    @Test
    void testViewProductsFromWishlist_Success() {
        Wishlist wishlist = new Wishlist(mockUser, mockProduct);
        List<Wishlist> wishlistItems = List.of(wishlist);

        when(wishlistRepository.findByUserEmail("a@gmail.com")).thenReturn(wishlistItems);

        List<Wishlist> result = wishlistService.viewProductsFromWishlist("a@gmail.com");

        assertEquals(1, result.size());
        assertEquals(mockProduct, result.get(0).getProduct());
    }

    @Test
    void testViewProductsFromWishlist_EmptyList() {
        when(wishlistRepository.findByUserEmail("a@gmail.com")).thenReturn(Collections.emptyList());

        assertThrows(ProductNotFoundException.class, () ->
                wishlistService.viewProductsFromWishlist("a@gmail.com"));
    }

    @Test
    void testRemoveProductFromWishlist_Success() {
        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.of(mockUser));
        when(productRepository.findById(101)).thenReturn(Optional.of(mockProduct));

        ResponseEntity<String> response = wishlistService.removeProductFromWishlist("a@gmail.com", 101);

        verify(wishlistRepository).deleteByUserAndProduct("a@gmail.com", 101);
        assertEquals("Product is successfully removed.", response.getBody());
    }

    @Test
    void testRemoveAllProductFromWishlist_Success() {
        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.of(mockUser));

        ResponseEntity<String> response = wishlistService.removeAllProductFromWishlist("a@gmail.com");

        verify(wishlistRepository).deleteByUser("a@gmail.com");
        assertEquals("All Products are successfully removed.", response.getBody());
    }

    @Test
    void testRemoveProductFromWishlist_UserNotFound() {
        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                wishlistService.removeProductFromWishlist("a@gmail.com", 101));
    }

    @Test
    void testRemoveProductFromWishlist_ProductNotFound() {
        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.of(mockUser));
        when(productRepository.findById(101)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () ->
                wishlistService.removeProductFromWishlist("a@gmail.com", 101));
    }

    @Test
    void testRemoveAllProductFromWishlist_UserNotFound() {
        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                wishlistService.removeAllProductFromWishlist("a@gmail.com"));
    }
}
