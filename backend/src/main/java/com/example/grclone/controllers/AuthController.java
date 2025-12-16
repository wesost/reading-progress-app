package com.example.grclone.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.RestController;

import com.example.grclone.dtos.UserLoginRequestDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


// handles login/logout 
@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // login reqs
    @PostMapping("/auth")
    public ResponseEntity<String> login(@RequestBody UserLoginRequestDto userLoginRequestDto, HttpServletRequest request){
        Authentication auth = new UsernamePasswordAuthenticationToken(
            userLoginRequestDto.getUsername(),
            userLoginRequestDto.getPassword()
        );

        try {
            // store authentication result
            Authentication authResult = authenticationManager.authenticate(auth);
            // add authentication to spring security context
            SecurityContextHolder.getContext().setAuthentication(authResult);
            //check if session alr exits via cookie, or creates a new one
            HttpSession session = request.getSession(true);
            // make authentication persist across requests
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            return ResponseEntity.ok("Login successful"); // 
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    } 
}
