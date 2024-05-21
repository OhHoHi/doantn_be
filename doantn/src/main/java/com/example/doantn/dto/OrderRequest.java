package com.example.doantn.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest{
    private Long userId;
    private int status;
    private double totalAmount;
    private List<OrderItemDTO> orderItems;
    private Long addressId; // Thêm ID của địa chỉ


    // Getters, setters
}