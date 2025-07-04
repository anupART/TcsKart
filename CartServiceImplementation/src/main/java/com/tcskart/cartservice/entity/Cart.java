package com.tcskart.cartservice.entity;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Schema(description = "Represents a shopping cart for a user")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the cart", example = "1")
    private Integer id;

    @Schema(description = "Email associated with the cart owner", example = "customer@example.com")
    private String email;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @Schema(description = "List of items in the cart")
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart(int id, String email, List<CartItem> cartItems) {
        super();
        this.id = id;
        this.email = email;
        this.cartItems = cartItems;
    }

    public Cart() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
