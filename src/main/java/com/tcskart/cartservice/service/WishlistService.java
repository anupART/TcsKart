package com.tcskart.cartservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcskart.cartservice.entity.Product;
import com.tcskart.cartservice.entity.User;
import com.tcskart.cartservice.entity.Wishlist;
import com.tcskart.cartservice.exception.AlreadyProductMyWishList;
import com.tcskart.cartservice.exception.ProductNotFoundException;
import com.tcskart.cartservice.exception.UserNotFoundException;
import com.tcskart.cartservice.repository.ProductRepository;
import com.tcskart.cartservice.repository.UserRepository;
import com.tcskart.cartservice.repository.WishlistRepository;

import jakarta.transaction.Transactional;

@Service
public class WishlistService {

	WishlistRepository wishlistRepository;

	UserRepository userRepository;

	ProductRepository productRepository;

	public WishlistService(WishlistRepository wishlistRepository, UserRepository userRepository,
			ProductRepository productRepository) {
		this.wishlistRepository = wishlistRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}

	public Wishlist addProductToWishlist(String userEmail, Integer productId) {

		Optional<User> user = userRepository.findByEmail(userEmail);

		user.orElseThrow(() -> new UserNotFoundException());

		Optional<Product> product = productRepository.findById(productId);

		product.orElseThrow(() -> new ProductNotFoundException());
		Integer checkProduct=0;
		
		 checkProduct = wishlistRepository.findByUserEmailAndProductId(userEmail, productId);
		 //System.out.println(checkProduct);
		if(checkProduct!=null)
		{
			throw new AlreadyProductMyWishList();
		}
		Wishlist wishList = new Wishlist(user.get(), product.get());
		return wishlistRepository.save(wishList);
	}

	public List<Wishlist> viewProductsFromWishlist(String userEmail) {

		List<Wishlist> wishlistItems = wishlistRepository.findByUserEmail(userEmail);

		if (wishlistItems.isEmpty()) {
			throw new ProductNotFoundException();
		}

		return wishlistItems;
	}
	@Transactional
	public ResponseEntity<String> removeProductFromWishlist(String userEmail, Integer productId) {

		Optional<User> user = userRepository.findByEmail(userEmail);

		user.orElseThrow(() -> new UserNotFoundException());

		Optional<Product> product = productRepository.findById(productId);

		product.orElseThrow(() -> new ProductNotFoundException());

		wishlistRepository.deleteByUserAndProduct(userEmail, productId);

		return new ResponseEntity<>("Product is successfully removed.", HttpStatus.OK);
	}
	@Transactional
	public ResponseEntity<String> removeAllProductFromWishlist(String userEmail) {

		Optional<User> user = userRepository.findByEmail(userEmail);

		user.orElseThrow(() -> new UserNotFoundException());

		wishlistRepository.deleteByUser(userEmail);

		return new ResponseEntity<>("All Products are successfully removed.", HttpStatus.OK);
	}
}
