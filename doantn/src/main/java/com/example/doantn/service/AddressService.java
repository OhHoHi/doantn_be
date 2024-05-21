package com.example.doantn.service;

import com.example.doantn.dto.AddressDTO;
import com.example.doantn.dto.AddressDTOshow;
import com.example.doantn.entity.Address;
import com.example.doantn.entity.User;
import com.example.doantn.repository.AddressRepository;
import com.example.doantn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    public Address addAddress(AddressDTO addressDTO) {
        User user = userRepository.findById(addressDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + addressDTO.getUserId()));
        Address address = new Address();
        address.setCity(addressDTO.getCity());
        address.setDistrict(addressDTO.getDistrict());
        address.setWard(addressDTO.getWard());
        address.setStreet(addressDTO.getStreet());
        address.setFullName(addressDTO.getFullName());
        address.setPhone(addressDTO.getPhone());
        address.setUser(user);

        return addressRepository.save(address);
    }

    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }


    public void updateAddress(Long userId, Long addressId, AddressDTOshow addressUpdateDTO) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            address.setCity(addressUpdateDTO.getCity());
            address.setDistrict(addressUpdateDTO.getDistrict());
            address.setWard(addressUpdateDTO.getWard());
            address.setStreet(addressUpdateDTO.getStreet());
            address.setFullName(addressUpdateDTO.getFullName());
            address.setPhone(addressUpdateDTO.getPhone());
            addressRepository.save(address);
        } else {
            throw new RuntimeException("Address not found with id: " + addressId);
        }
    }

    public void deleteAddress(Long userId, Long addressId) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            // Kiểm tra xem địa chỉ này thuộc về người dùng có userId được cung cấp hay không
            if (!address.getUser().getId().equals(userId)) {
                throw new RuntimeException("Address does not belong to the user with id: " + userId);
            }
            addressRepository.delete(address);
        } else {
            throw new RuntimeException("Address not found with id: " + addressId);
        }
    }


}