package com.tcs.tcskart.product.exceptions;

public class UserNotFound extends RuntimeException {
	public UserNotFound() {
        super("User not found with the provided email.");
    }

    public UserNotFound(String message) {
        super(message);
    }
}