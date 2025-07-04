package com.tcskart.cartservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Schema(description = "Represents a mapping of a product that is not available for a specific pincode")
public class ProductNotAvailableLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the record", example = "1")
    private Integer id;

    @Schema(description = "Pincode where the product is not available", example = "600001")
    private Integer pincode;

    @Schema(description = "ID of the product that is not available", example = "101")
    private int productId;

    public ProductNotAvailableLocation() {}

    public ProductNotAvailableLocation(Integer id, Integer pincode, int productId) {
        this.id = id;
        this.pincode = pincode;
        this.productId = productId;
    }

    public ProductNotAvailableLocation(Integer pincode, int productId) {
        super();
        this.pincode = pincode;
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
