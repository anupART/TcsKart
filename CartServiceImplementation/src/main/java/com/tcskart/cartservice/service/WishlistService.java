package com.tcskart.cartservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcskart.cartservice.dto.ProductShare;
import com.tcskart.cartservice.entity.Wishlist;
import com.tcskart.cartservice.exception.AlreadyProductMyWishList;
import com.tcskart.cartservice.exception.ProductNotFoundException;
import com.tcskart.cartservice.repository.WishlistRepository;

import jakarta.transaction.Transactional;

@Service
public class WishlistService {

	WishlistRepository wishlistRepository;

	ProductServiceFeignImpl productService;

	UserServiceFeignImpl userService;

	public WishlistService(WishlistRepository wishlistRepository, ProductServiceFeignImpl productService,
			UserServiceFeignImpl userService) {
		super();
		this.wishlistRepository = wishlistRepository;
		this.productService = productService;
		this.userService = userService;
	}

	public Wishlist addProductToWishlist(String userEmail, Integer productId) {

		ProductShare product = productService.getDetailsByProductId(productId);

		if (product == null) {
			throw new ProductNotFoundException();
		}

		Integer checkProduct = 0;

		checkProduct = wishlistRepository.findByUserEmailAndProductId(userEmail, productId);
		// System.out.println(checkProduct);
		if (checkProduct != null) {
			throw new AlreadyProductMyWishList();
		}
		Wishlist wishList = new Wishlist(userEmail, product.getProductId());
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
	public ResponseEntity<String> removeProductFromWishlist(String userEmail, Integer wishlistId) {

		Optional<Wishlist> wishlistProduct = wishlistRepository.findById(wishlistId);
		
		if(wishlistProduct.isEmpty()) {
			throw new ProductNotFoundException();
		}
		wishlistRepository.deleteByUserAndProduct(userEmail, wishlistId);

		return new ResponseEntity<>("Product is successfully removed.", HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<String> removeAllProductFromWishlist(String userEmail) {

		wishlistRepository.deleteByUser(userEmail);

		return new ResponseEntity<>("All Products are successfully removed.", HttpStatus.OK);
	}
}
