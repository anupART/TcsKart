package com.tcskart.cartservice.service;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.tcskart.cartservice.entity.Cart;
import com.tcskart.cartservice.entity.CartItem;
import com.tcskart.cartservice.entity.Product;
import com.tcskart.cartservice.entity.User;
import com.tcskart.cartservice.repository.CartItemRepository;
import com.tcskart.cartservice.repository.CartRepository;
import com.tcskart.cartservice.repository.ProductRepository;
import com.tcskart.cartservice.repository.UserRepository;
import com.tcskart.cartservice.util.ProductCategory;

@ExtendWith(MockitoExtension.class)
public class CartServiceTests {

	@Mock
	UserRepository userRepository;
	
	@Mock
	ProductRepository productRepository;
	
	@Mock
	CartRepository cartRepository;
	
	@Mock
	CartItemRepository cartItemRepository;
	
	@InjectMocks
	CartService cartService;
	
	Optional<User> user;
	Optional<Product> product1;
	Optional<Product> product2;
	Optional<Cart> cart = Optional.of(new Cart());
	
	@BeforeAll
	void setData() {
		user = Optional.of(new User("Rakshit Gobbi", "rakshitgobbi@gmail.com",890565234,"ra"));
		user.get().setId(1);
		
		product1 = Optional.of(new Product("Pen","Writing", 10.0, 10,ProductCategory.STATIONERY,4.5));
		product1.get().setProductId(100);
		
		product1 = Optional.of(new Product("Pen","Writing", 10.0, 10,ProductCategory.STATIONERY,4.5));
		product1.get().setProductId(100);
		
		cart.get().setId(1000);
		cart.get().setId(10);
		cart.get().setUser(user.get());
		
		
	}
	
	
	@Test
	void addProductToCart() {
		//userEmail, ProductId, quantity
		
		when(userRepository.findByEmail("rakshitgobbi@gmail.com")).thenReturn(user);
		
		when(productRepository.findById(100)).thenReturn(product1);
		
		when(cartRepository.findById(cart.get().getId())).thenReturn(cart);
		
		when(cartItemRepository.findByCartId(1000)).thenReturn((List<CartItem>) cart.get());
		
		
	}
}
