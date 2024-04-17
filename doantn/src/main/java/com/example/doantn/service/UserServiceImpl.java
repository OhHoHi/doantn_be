package com.example.doantn.service;

import ch.qos.logback.core.model.Model;
import com.example.doantn.dto.UserDTO;
import com.example.doantn.entity.Role;
import com.example.doantn.entity.User;
import com.example.doantn.repository.RoleRepository;
import com.example.doantn.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

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
}
