package com.example.grclone.services;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.grclone.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.grclone.mappers.UserMapper;
import com.example.grclone.dtos.UserDto;
import com.example.grclone.dtos.UserResponseDto;
import com.example.grclone.repositories.UserRepository;


@Service
public class UserService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto createUser(UserDto userDto) { 
        // check if user email already exists or if username already exists
        // if so, error on duplicate email/username
        // otherwise create and save new user

        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username already taken");
        }

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account with provided email already exists");
        }

        User user = userMapper.toEntity(userDto, passwordEncoder.encode(userDto.getPassword()));
        User saved = userRepository.save(user);
        return userMapper.toResponseDto(saved);
    }

    // for lookup on login request see AuthController
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

}
