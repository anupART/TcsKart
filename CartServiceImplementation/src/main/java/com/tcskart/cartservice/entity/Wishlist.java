package com.tcskart.cartservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Schema(description = "Represents a product added by a user to their wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique ID for the wishlist item", example = "1")
    private Integer wishlistId;

    @Schema(description = "User's email associated with the wishlist", example = "user@example.com")
    private String email;

    @Schema(description = "Product ID added to the wishlist", example = "101")
    private int productId;

    public Wishlist(Integer wishlistId, String email, int productId) {
        super();
        this.wishlistId = wishlistId;
        this.email = email;
        this.productId = productId;
    }

    public Wishlist() {}

    public Wishlist(String email, int productId) {
        super();
        this.email = email;
        this.productId = productId;
    }

    public Integer getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Integer wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
