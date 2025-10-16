package com.example.grclone.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    
        if (!userDto.getUsername().matches("^[A-Za-z0-9_-]{3,20}$")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username: must be 3-20 characters, including only numbers, letters, dashes, and underscores");
        }

        if (!userDto.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email");
        }


        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username already taken");
        }

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account with provided email already exists");
        }

        User user = userMapper.toEntity(userDto, passwordEncoder.encode(userDto.getPassword()));

        if (user.getRole() == null) {
            user.setRole("ROLE_USER");
        }
        User saved = userRepository.save(user);
        return userMapper.toResponseDto(saved);
    }

    // for lookup on login request see AuthController
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public Page<UserResponseDto> getAllUsers(Pageable pageable) {
        Page<User> results = userRepository.findAll(pageable);
        return results.map(userMapper::toResponseDto);
        
    }

    public Page<UserResponseDto> searchUsers(String username, Pageable pageable) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("please supply a username");
        }
        Page<User> results = userRepository.findByUsernameContainingIgnoreCase(username, pageable);
        return results.map(userMapper::toResponseDto);
    }
    
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"
            ));
        userRepository.delete(user);
    }

}
