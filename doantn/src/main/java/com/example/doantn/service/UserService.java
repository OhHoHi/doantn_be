package com.example.doantn.service;

import com.example.doantn.dto.UserDTO;
import com.example.doantn.dto.UserUpdateRequest;
import com.example.doantn.entity.Role;
import com.example.doantn.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addToUser(String username ,String rolename);
    User getUserById(Long userId);
    void createUser(UserDTO userDTO);
    void changePassword(Long userId, String oldPassword, String newPassword);

    User updateUser(Long userId, UserUpdateRequest userUpdateRequest);

    List<User> getAllUsers();

    boolean existsById( Long userId);


}
