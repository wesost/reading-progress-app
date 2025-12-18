package com.example.grclone.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.example.grclone.dtos.UserDto;
import com.example.grclone.dtos.UserResponseDto;
import com.example.grclone.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserDto userDto) {
        UserResponseDto created = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping()
    public Page<UserResponseDto> getUsers(
        @PageableDefault(sort = "username")
        Pageable pageable
    ) {
        return userService.getAllUsers(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserResponseDto>> searchUsers(
        @RequestParam(value = "username", required = true) String username,
        Pageable pageable
    ) {
        Page<UserResponseDto> users = userService.searchUsers(username, pageable);
        return ResponseEntity.ok(users);
    }

    
    // delete user endpoint
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(
        @PathVariable String username
    ) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("token") String token) {
        userService.verifyUser(token);
        return ResponseEntity.ok("Email verified successfully");
    }

    
}
