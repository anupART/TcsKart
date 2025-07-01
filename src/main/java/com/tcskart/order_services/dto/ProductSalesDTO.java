package com.tcskart.order_services.dto;

import com.tcskart.order_services.bean.Product;

public class ProductSalesDTO {
    private Product product;
    private Long totalQuantitySold;

    public ProductSalesDTO(Product product, Long totalQuantitySold) {
        this.product = product;
        this.totalQuantitySold = totalQuantitySold;
    }

    public Product getProduct() {
        return product;
    }

    public Long getTotalQuantitySold() {
        return totalQuantitySold;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setTotalQuantitySold(Long totalQuantitySold) {
        this.totalQuantitySold = totalQuantitySold;
    }
}
