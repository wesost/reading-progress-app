package com.example.grclone.mappers;

import org.springframework.stereotype.Component;

import com.example.grclone.entities.User;
import com.example.grclone.dtos.UserDto;
import com.example.grclone.dtos.UserResponseDto;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        return new UserDto(
            user.getUsername(),
            user.getEmail(),
            user.getPassword()
        );
    }

    public User toEntity(UserDto userDto, String hashedPassword) {
        return new User( // JPA handles id generation
            userDto.getUsername(),
            userDto.getEmail(),
            hashedPassword
        );
    }

    public UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(user.getUsername(), user.getEmail());
    }


    // TODO: toDtoList list users for future admin functionality
    
}
