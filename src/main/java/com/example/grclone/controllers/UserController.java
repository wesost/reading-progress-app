package com.example.grclone.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.grclone.dtos.UserDto;
import com.example.grclone.dtos.UserResponseDto;
import com.example.grclone.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/users")
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
    public List<UserResponseDto> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDto>> searchUsers(
        @RequestParam(value = "username", required = true) String username
    ) {
        List<UserResponseDto> users = userService.searchUsers(username);
        return ResponseEntity.ok(users);
    }

    
    // delete user endpoint
    // @DeleteMapping
    // public ResponseEntity<UserResponseDto> deleteUser()


    //update user...low priority
    
}
