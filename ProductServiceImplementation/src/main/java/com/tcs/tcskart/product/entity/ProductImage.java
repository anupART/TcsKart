package com.tcs.tcskart.product.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Schema(description = "Represents an image associated with a product")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique ID for the image", example = "1001")
    private Integer imageId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference
    @Schema(description = "The product to which this image belongs")
    private Product product;

    @Schema(description = "URL of the product image", example = "https://example.com/images/product1.jpg")
    private String imageUrl;

    public ProductImage() {
    }

    public ProductImage(Integer imageId, Product product, String imageUrl) {
        super();
        this.imageId = imageId;
        this.product = product;
        this.imageUrl = imageUrl;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
