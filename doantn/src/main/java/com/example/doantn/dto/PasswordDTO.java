package com.example.doantn.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PasswordDTO {
    private String oldPassword;
    private String newPassword;
}
