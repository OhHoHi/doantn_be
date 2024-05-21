package com.example.doantn.controller;

import com.example.doantn.dto.AddressDTO;
import com.example.doantn.dto.AddressDTOshow;
import com.example.doantn.entity.Address;
import com.example.doantn.entity.User;
import com.example.doantn.service.AddressService;
import com.example.doantn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<Address> addAddress(@RequestBody AddressDTO addressDTO) {
        Address address = addressService.addAddress(addressDTO);
        return ResponseEntity.ok(address);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address>> getAddressesByUserId(@PathVariable Long userId) {
        List<Address> addresses = addressService.getAddressesByUserId(userId);
        return ResponseEntity.ok(addresses);
    }

    @PutMapping("/update/{addressId}/{userId}")
    public ResponseEntity<String> updateAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @RequestBody AddressDTOshow addressUpdateDTO) {
        User user = userService.getUserById(userId);
        addressService.updateAddress(userId, addressId, addressUpdateDTO);
        return ResponseEntity.ok("Address updated successfully.");
    }

    @DeleteMapping("/delete/{addressId}/{userId}")
    public ResponseEntity<String> deleteAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId) {
        User user = userService.getUserById(userId);
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.ok("Address deleted successfully.");
    }
}