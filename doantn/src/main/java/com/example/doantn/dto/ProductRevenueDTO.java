package com.example.doantn.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRevenueDTO {
    private Long productId;
    private String productName;
    private double totalRevenue;

    public ProductRevenueDTO(Long productId, String productName, double totalRevenue) {
        this.productId = productId;
        this.productName = productName;
        this.totalRevenue = totalRevenue;
    }

}
