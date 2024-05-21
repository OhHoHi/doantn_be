package com.example.doantn.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private String city;
    private String district;
    private String ward;
    private String street;
    private String fullName;
    private String phone;
    private Long userId; // ID của người dùng mà địa chỉ này liên kết với
}