package com.example.grclone.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.RestController;

import com.example.grclone.dtos.UserLoginRequestDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


// handles login/logout 
// TODO: logout 
@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // login reqs
    @PostMapping("/auth")
    public ResponseEntity<String> login(@RequestBody UserLoginRequestDto userLoginRequestDto){
        Authentication auth = new UsernamePasswordAuthenticationToken(
            userLoginRequestDto.getUsername(),
            userLoginRequestDto.getPassword()
        );


        /*
            // Optional: get the authenticated UserDetails
            User user = (User) authResult.getPrincipal();
            UserResponseDto dto = userMapper.toDto(user); // TODO: maybe needed to display logged in user's username on frontend.. ? low priority
         */
        try {
            authenticationManager.authenticate(auth);
            return ResponseEntity.ok("Login successful"); // 
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    } 
}
