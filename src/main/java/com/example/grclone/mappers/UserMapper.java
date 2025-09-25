package com.example.grclone.mappers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            user.getPassword(),
            user.getRole()
        );
    }

    public User toEntity(UserDto userDto, String hashedPassword) {
        return new User( // JPA handles id generation
            userDto.getUsername(),
            userDto.getEmail(),
            hashedPassword,
            Optional.ofNullable(userDto.getRole()).orElse("ROLE_USER")
        );
    }

    public UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(user.getUsername(),
        user.getEmail(),
        user.getRole()
        );
    }

    public List<UserResponseDto> toUserResponseDtoList(List<User> users) {
        return users.stream()
        .map(this::toResponseDto)
        .collect(Collectors.toList());
        }


    // TODO: toDtoList list users for future admin functionality
    
}
