package com.example.doantn.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {
    private Long brandId;
    private int page;
    private int size;
    private String sort;
    private String productName;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private int diemCanBang;
    public ProductRequest() {
        this.page = 0; // Giá trị mặc định cho page
        this.size = 10; // Giá trị mặc định cho size
        this.sort = "id_desc";
        this.diemCanBang = 0;
    }
    // Getters và setters
}