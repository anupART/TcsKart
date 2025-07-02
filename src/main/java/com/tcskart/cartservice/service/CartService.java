package com.tcskart.cartservice.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.tcskart.cartservice.entity.Cart;
import com.tcskart.cartservice.entity.CartItem;
import com.tcskart.cartservice.entity.Product;
import com.tcskart.cartservice.entity.User;
import com.tcskart.cartservice.exception.CartNotFoundException;
import com.tcskart.cartservice.exception.NoEnoughItemOfProductException;
import com.tcskart.cartservice.exception.NoEnoughStockException;
import com.tcskart.cartservice.exception.NoItemsFoundInCartException;
import com.tcskart.cartservice.exception.NoNegativeException;
import com.tcskart.cartservice.exception.ProductNotFoundException;
import com.tcskart.cartservice.exception.UserNotFoundException;
import com.tcskart.cartservice.repository.CartItemRepository;
import com.tcskart.cartservice.repository.CartRepository;
import com.tcskart.cartservice.repository.ProductRepository;
import com.tcskart.cartservice.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {

	UserRepository userRepository;

	ProductRepository productRepository;

	CartRepository cartRepository;

	CartItemRepository cartItemRepository;

	public CartService() {
	}

	@Autowired
	public CartService(UserRepository userRepository, ProductRepository productRepository,
			CartRepository cartRepository, CartItemRepository cartItemRepository) {
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
		this.cartItemRepository = cartItemRepository;
	}

	public Cart addProductToCart(String userEmail, Integer productId, int quantity) {

		if (userEmail.length() == 0 || productId < 0 || quantity < 0) {
			throw new NoNegativeException();
		}
		Optional<User> user = userRepository.findByEmail(userEmail);

		user.orElseThrow(() -> new UserNotFoundException());

		Cart cart = cartRepository.findByUser(user.get()).orElseGet(() -> {
			Cart newCart = new Cart();
			newCart.setUser(user.get());
			return cartRepository.save(newCart);
		});

		Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException());

		if (product.getQuantity() < quantity) {
			throw new NoEnoughStockException();
		}

		product.setQuantity(product.getQuantity() - quantity);
		productRepository.save(product);

		Optional<CartItem> existingItem = cart.getCartItems().stream()
				.filter(item -> item.getProduct().getProductId().equals(productId)).findFirst();

		if (existingItem.isPresent()) {
			CartItem item = existingItem.get();
			item.setQuantity(item.getQuantity() + quantity);
		} else {
			CartItem newItem = new CartItem();
			newItem.setCart(cart);
			newItem.setProduct(product);
			newItem.setQuantity(quantity);
			cart.getCartItems().add(newItem);
		}

		return cartRepository.save(cart);

	}

	public List<CartItem> getAllItemsFromCart(String userEmail) {

		Optional<User> user = userRepository.findByEmail(userEmail);

		user.orElseThrow(() -> new UserNotFoundException());

		Optional<Cart> cart = cartRepository.findById(user.get().getCart().getId());

		if (cart.isEmpty()) {
			throw new NoItemsFoundInCartException();
		}
		return cart.get().getCartItems();
	}

	@Transactional
	public ResponseEntity<String> removeAllItemsFromCart(String userEmail) {

		Optional<User> user = userRepository.findByEmail(userEmail);

		user.orElseThrow(() -> new UserNotFoundException());

		Integer cartId = user.get().getCart().getId();
		List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);

		for (CartItem cartItem : cartItems) {
			Product product = cartItem.getProduct();
			product.setQuantity(product.getQuantity() + cartItem.getQuantity());
			productRepository.save(product);
		}

		cartItemRepository.deleteByCartId(cartId);

		return new ResponseEntity<>("All Items from Cart are removed.", HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<String> removeProductFromCartByQuantity(String userEmail, Integer productId,
			Integer quantity) {

		Optional<User> user = userRepository.findByEmail(userEmail);

		user.orElseThrow(() -> new UserNotFoundException());

		Integer cartId = user.get().getCart().getId();

		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException());

		List<CartItem> cartItems = cart.getCartItems();

		Optional<CartItem> cartItemQuantity = cartItems.stream()
				.filter(ref -> ref.getProduct().getProductId().equals(productId)).findFirst();

		if (cartItemQuantity.isEmpty()) {
			throw new NoItemsFoundInCartException();
		}

		if (quantity > cartItemQuantity.get().getQuantity()) {
			throw new NoEnoughItemOfProductException();
		} else {

			Optional<Product> product = productRepository.findById(productId);
			Optional<Product> quantities = productRepository.findById(productId);

			product.get().setQuantity(quantities.get().getQuantity() + quantity);
			productRepository.save(product.get());

			cartItemQuantity.get().setQuantity(cartItemQuantity.get().getQuantity() - quantity);
			cartItemRepository.save(cartItemQuantity.get());

		}

		if (cartItemQuantity.get().getQuantity() == 0) {
			System.out.println("Deleting CartItem with ID: " + cartItemQuantity.get().getId());
			cart.getCartItems().remove(cartItemQuantity.get());
			cartItemRepository.deleteById(cartItemQuantity.get().getId());
			cartRepository.save(cart);
		}
		return new ResponseEntity<>("Removed " + quantity + " of products from cart", HttpStatus.OK);
	}

	public ResponseEntity<String> addProductFromCartByQuantity(String userEmail, Integer productId, Integer quantity) {

		Optional<User> user = userRepository.findByEmail(userEmail);

		user.orElseThrow(() -> new UserNotFoundException());

		Integer cartId = user.get().getCart().getId();

		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException());

		this.addProductToCart(userEmail, productId, quantity);

		return new ResponseEntity<>("Increased " + quantity + " of products from cart", HttpStatus.OK);
	}

	public ResponseEntity<String> removeProductFromCart(String userEmail, Integer cartItemId) {
		
		if (userEmail.length() == 0 || cartItemId < 0) {
			throw new NoNegativeException();
		}
		Optional<User> user = userRepository.findByEmail(userEmail);

		user.orElseThrow(() -> new UserNotFoundException());
		
		Integer cartId = user.get().getCart().getId();
		
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException());

		List<CartItem> cartItems = cart.getCartItems();

		Optional<CartItem> cartItem = cartItems.stream().filter(item -> item.getId().equals(cartItemId)).findFirst();

		if (cartItem.isEmpty()) {
			return new ResponseEntity<>("Cart Item doesn't exist.", HttpStatus.BAD_REQUEST);
		} else {

			cartItems.remove(cartItem.get());

			cartItemRepository.deleteById(cartItemId);

			cartRepository.save(cart);
			return new ResponseEntity<>("Cart Item deleted successfully", HttpStatus.OK);
		}
	}
}
