package com.example.doantn.service;

import com.example.doantn.dto.UserDTO;
import com.example.doantn.entity.Role;
import com.example.doantn.entity.User;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addToUser(String username ,String rolename);

    void createUser(UserDTO userDTO);
}
