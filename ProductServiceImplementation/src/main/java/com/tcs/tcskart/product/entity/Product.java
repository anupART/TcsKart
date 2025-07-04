package com.tcs.tcskart.product.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tcs.tcskart.product.utility.ProductCategory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Schema(description = "Represents a product in the TCSKart system")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique ID of the product", example = "101")
    private Integer productId;

    @Column(nullable = false)
    @Schema(description = "Name of the product", example = "Samsung Galaxy M15")
    private String productName;

    @Column(nullable = false)
    @Schema(description = "Product description", example = "6.5-inch AMOLED, 6000mAh battery")
    private String description;

    @Column(nullable = false)
    @Schema(description = "Price of the product", example = "14999.00")
    private Double productPrice;

    @Column(nullable = false)
    @Schema(description = "Available quantity in stock", example = "50")
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Product category", example = "ELECTRONICS")
    private ProductCategory productCategory;

    @Column(nullable = false)
    @Schema(description = "Average user rating of the product", example = "4.5")
    private Double productRating;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Schema(description = "List of images associated with the product")
    private List<ProductImage> images;

    public Product() {}

    public Product(String productName, String description, Double productPrice, Integer quantity,
                   ProductCategory productCategory, Double productRating) {
        super();
        this.productName = productName;
        this.description = description;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.productCategory = productCategory;
        this.productRating = productRating;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Double getProductRating() {
        return productRating;
    }

    public void setProductRating(Double productRating) {
        this.productRating = productRating;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }
}
