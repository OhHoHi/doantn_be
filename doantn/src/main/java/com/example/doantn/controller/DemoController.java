package com.example.doantn.controller;

import com.example.doantn.auth.AuthenticationRequest;
import com.example.doantn.auth.AuthenticationResponse;
import com.example.doantn.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

    private AuthenticationService authenticationService;
    @PostMapping("/test")
    public ResponseEntity<String> login(){
        return  ResponseEntity.ok("Authentication and Authorization is succeeded");
    }
}
