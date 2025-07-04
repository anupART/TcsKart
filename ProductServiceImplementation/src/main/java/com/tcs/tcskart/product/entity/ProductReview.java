package com.tcs.tcskart.product.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Schema(description = "Represents a review left by a user for a specific product")
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Auto-generated ID of the review", example = "1")
    private Long id;

    @ManyToOne
    @Schema(description = "The product to which this review belongs")
    private Product product;

    @Schema(description = "Email of the user who submitted the review", example = "user@example.com")
    private String email;

    @Schema(description = "Rating given by the user", example = "4.5")
    private double rating;

    @Schema(description = "Review text submitted by the user", example = "Great product with long battery life!")
    private String reviewText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
