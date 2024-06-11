package com.example.doantn.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CancelOrderRequest {
    private Long orderId;
    private List<OrderItemDto> orderItems;

    // Getters and Setters

    public static class OrderItemDto {
        private Long productId;
        private int quantity;

        // Getters and Setters

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
