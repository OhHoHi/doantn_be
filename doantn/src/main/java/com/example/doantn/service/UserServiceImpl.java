package com.example.doantn.service;

import com.example.doantn.dto.UserDTO;
import com.example.doantn.dto.UserUpdateRequest;
import com.example.doantn.entity.Role;
import com.example.doantn.entity.User;
import com.example.doantn.repository.RoleRepository;
import com.example.doantn.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addToUser(String username, String rolename) {
        User user = userRepository.findByEmail(username).get();
        Role role = roleRepository.findByName(rolename);
        user.getRoles().add(role);
    }
    @Override
    public void createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email da ton tai");
        }
        User newUser = new User(userDTO.getFull_name(), userDTO.getUser_name(),
                userDTO.getEmail(), userDTO.getPassword());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        // Tìm Role ROLE_USER từ cơ sở dữ liệu
        Role userRole = roleRepository.findByName("ROLE_USER");
        // Gán Role cho người dùng mới
        newUser.getRoles().add(userRole);
        userRepository.save(newUser);
    }
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public User updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Cập nhật thông tin người dùng nếu được cung cấp
        if (userUpdateRequest.getFullName() != null && !userUpdateRequest.getFullName().trim().isEmpty()) {
            user.setFull_name(userUpdateRequest.getFullName().trim());
        }
        if (userUpdateRequest.getEmail() != null && !userUpdateRequest.getEmail().trim().isEmpty()) {
            user.setEmail(userUpdateRequest.getEmail().trim());
        }
        if (userUpdateRequest.getUserName() != null && !userUpdateRequest.getUserName().trim().isEmpty()) {
            user.setUser_name(userUpdateRequest.getUserName().trim());
        }
        return userRepository.save(user);
    }

    @Override
    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



}
