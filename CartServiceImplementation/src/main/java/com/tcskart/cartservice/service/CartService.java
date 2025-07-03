package com.tcskart.cartservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcskart.cartservice.dto.ProductShare;
import com.tcskart.cartservice.entity.Cart;
import com.tcskart.cartservice.entity.CartItem;
import com.tcskart.cartservice.exception.CartNotFoundException;
import com.tcskart.cartservice.exception.NoEnoughItemOfProductException;
import com.tcskart.cartservice.exception.NoEnoughStockException;
import com.tcskart.cartservice.exception.NoItemsFoundInCartException;
import com.tcskart.cartservice.exception.NoNegativeException;
import com.tcskart.cartservice.exception.ProductNotFoundException;
import com.tcskart.cartservice.exception.UserNotFoundException;
import com.tcskart.cartservice.repository.CartItemRepository;
import com.tcskart.cartservice.repository.CartRepository;


import jakarta.transaction.Transactional;

@Service
public class CartService {
	
	CartRepository cartRepository;

	CartItemRepository cartItemRepository;
	
	ProductServiceFeignImpl productService;
	
	UserServiceFeignImpl userService;

	public CartService() {
	}

	@Autowired
	public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
			ProductServiceFeignImpl productService, UserServiceFeignImpl userService) {
		super();
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.productService = productService;
		this.userService = userService;
	}



//	public Cart addProductToCart(String userEmail, Integer productId, int quantity) {
//
//		if (userEmail.length() == 0 || productId < 0 || quantity < 0) {
//			throw new NoNegativeException();
//		}
//
//		Cart cart = cartRepository.findByEmail(userEmail).orElseGet(() -> {
//			Cart newCart = new Cart();
//			newCart.setEmail(userEmail);
//			return cartRepository.save(newCart);
//		});
//
//		Optional<ProductShare> product = Optional.of(productService.getDetailsByProductId(productId));  
//		System.out.println(product.get().getProductId());
//		if(product.isEmpty()) {
//			throw new ProductNotFoundException();
//		}
//		
//		if (product.get().getQuantity() < quantity) {
//			throw new NoEnoughStockException();
//		}
//
//		Optional<CartItem> existingItem = cart.getCartItems().stream()
//				.filter(item -> item.getProductId().equals(productId)).findFirst();
//
//		if (existingItem.isPresent()) {
//			CartItem item = existingItem.get();
//			item.setQuantity(item.getQuantity() + quantity);
//		} else {
//			CartItem newItem = new CartItem();
//			newItem.setCart(cart);
//			newItem.setProductId(product.get().getProductId());
//			System.out.println(newItem.getProductId());
//			newItem.setQuantity(quantity);
//			cart.getCartItems().add(newItem);
//		}
//
//		return cartRepository.save(cart);
//
//	}
	public Cart addProductToCart(String userEmail, Integer productId, int quantity) {

	    if (userEmail.length() == 0 || productId < 0 || quantity < 0) {
	        throw new NoNegativeException();
	    }

	    Cart cart = cartRepository.findByEmail(userEmail).orElseGet(() -> {
	        Cart newCart = new Cart();
	        newCart.setEmail(userEmail);
	        return cartRepository.save(newCart);
	    });

	    if (cart.getCartItems() == null) {
	        cart.setCartItems(new ArrayList<>());
	    }

	    ProductShare product = productService.getDetailsByProductId(productId);
	    //System.out.println(product.getProductId());
	    if (product == null) {
	        throw new ProductNotFoundException();
	    }

	    if (product.getQuantity() < quantity) {
	        throw new NoEnoughStockException();
	    }

	    Optional<CartItem> existingItem = cart.getCartItems().stream()
	        .filter(item -> productId.equals(item.getProductId()))
	        .findFirst();

	    if (existingItem.isPresent()) {
	        CartItem item = existingItem.get();
	        item.setQuantity(item.getQuantity() + quantity);
	    } else {
	        CartItem newItem = new CartItem();
	        newItem.setCart(cart);
	        newItem.setProductId(product.getProductId());
	        newItem.setQuantity(quantity);
	        cart.getCartItems().add(newItem);
	    }

	    return cartRepository.save(cart);
	}

	public List<CartItem> getAllItemsFromCart(String userEmail) {


		Optional<Cart> cart = cartRepository.findByEmail(userEmail);
	
		if (cart.isEmpty()) {
			throw new NoItemsFoundInCartException();
		}
		return cart.get().getCartItems();
	}

	@Transactional
	public ResponseEntity<String> removeAllItemsFromCart(String userEmail) {
		Optional<Cart> cart = cartRepository.findByEmail(userEmail);
		Integer cartId =cart.get().getId();
		List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
		
		cartItemRepository.deleteByCartId(cartId);

		return new ResponseEntity<>("All Items from Cart are removed.", HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<String> removeProductFromCartByQuantity(String userEmail, Integer productId,
			Integer quantity) {

		Optional<Cart> cartForUser = cartRepository.findByEmail(userEmail); 
		Integer cartId = cartForUser.get().getId();

		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException());

		List<CartItem> cartItems = cart.getCartItems();

		Optional<CartItem> cartItemQuantity = cartItems.stream()
				.filter(ref -> ref.getProductId().equals(productId)).findFirst();

		if (cartItemQuantity.isEmpty()) {
			throw new NoItemsFoundInCartException();
		}

		if (quantity > cartItemQuantity.get().getQuantity()) {
			throw new NoEnoughItemOfProductException();
		} else {

			cartItemQuantity.get().setQuantity(cartItemQuantity.get().getQuantity() - quantity);
			cartItemRepository.save(cartItemQuantity.get());

		}

		if (cartItemQuantity.get().getQuantity() == 0) {
			cart.getCartItems().remove(cartItemQuantity.get());
			cartItemRepository.deleteById(cartItemQuantity.get().getId());
			cartRepository.save(cart);
		}
		return new ResponseEntity<>("Removed " + quantity + " of products from cart", HttpStatus.OK);
	}

	public ResponseEntity<String> addProductFromCartByQuantity(String userEmail, Integer productId, Integer quantity) {


		Optional<Cart> cartForUser = cartRepository.findByEmail(userEmail); 
		Integer cartId = cartForUser.get().getId();

		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException());
		
		ProductShare product = productService.getDetailsByProductId(productId);
		
		if(product == null) {
			new ProductNotFoundException();
		}

		if (product.getQuantity() < quantity) {
			throw new NoEnoughStockException();
		}

		this.addProductToCart(userEmail, productId, quantity);

		return new ResponseEntity<>("Increased " + quantity + " of products from cart", HttpStatus.OK);
	}

	public ResponseEntity<String> removeProductFromCart(String userEmail, Integer cartItemId) {
		
		if (userEmail.length() == 0 || cartItemId < 0) {
			throw new NoNegativeException();
		}

		Optional<Cart> cartForUser = cartRepository.findByEmail(userEmail); 
		Integer cartId = cartForUser.get().getId();

		
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
