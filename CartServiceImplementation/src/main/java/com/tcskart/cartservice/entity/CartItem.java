package com.tcskart.cartservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Schema(description = "Represents an individual item inside a user's cart")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the cart item", example = "101")
    private Integer id;

    @Schema(description = "Quantity of the product in the cart", example = "2")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    @Schema(description = "The cart to which this item belongs")
    private Cart cart;

    @Schema(description = "ID of the product added to the cart", example = "5001")
    private Integer productId;

    public CartItem(Integer id, Integer quantity, Cart cart, Integer productId) {
        super();
        this.id = id;
        this.quantity = quantity;
        this.cart = cart;
        this.productId = productId;
    }

    public CartItem() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
