package com.example.doantn.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String fullName;
    private String email;
    private String userName; // Thêm trường user_name
    // Thêm các trường thông tin cần cập nhật khác nếu cần
}