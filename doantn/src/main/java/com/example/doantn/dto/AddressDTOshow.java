package com.example.doantn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTOshow {
    private String city;
    private String district;
    private String ward;
    private String street;
    private String fullName;
    private String phone;
}
