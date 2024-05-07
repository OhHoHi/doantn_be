package com.example.doantn.controller;

import com.example.doantn.auth.AuthenticationRequest;
import com.example.doantn.auth.AuthenticationResponse;
import com.example.doantn.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

//    public AuthenticationController(AuthenticationService authenticationService) {
//        this.authenticationService = authenticationService;
//    }

//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
//        return  ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
//    }
@PostMapping("/login")
public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
    AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);
    return ResponseEntity.ok(response);
}




}
