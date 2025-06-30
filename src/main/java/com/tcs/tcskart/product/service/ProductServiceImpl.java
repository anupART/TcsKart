package com.tcs.tcskart.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcs.tcskart.product.entity.Product;
import com.tcs.tcskart.product.exception.InValidProductIdException;
import com.tcs.tcskart.product.exception.ProductNotFoundException;
import com.tcs.tcskart.product.repository.ProductRepository;
import com.tcs.tcskart.product.utility.ProductCategory;


@Service
public class ProductServiceImpl implements ProductService {

	ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewAllProducts() {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewProductById(Integer productId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub

	}

	@Override
	public ResponseEntity<String> deleteById(Integer productId) {

		if (productId < 0) {
			throw new InValidProductIdException();
		}

		Optional<Product> productDetails = productRepository.findById(productId);

		productDetails.orElseThrow(() -> new ProductNotFoundException());

		productRepository.deleteById(productId);

		return new ResponseEntity<>("Product Deletion Successfull.", HttpStatus.OK);
	}

	public List<Product> searchByProductCategory(ProductCategory productCategory) {

		return productRepository.findByProductCategory(productCategory);
	}
}
