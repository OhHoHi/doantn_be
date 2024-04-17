package com.example.doantn.dto;

import com.example.doantn.entity.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String full_name;
    private String user_name;
    private String email;
    private String password;
//    private Role role;

}
