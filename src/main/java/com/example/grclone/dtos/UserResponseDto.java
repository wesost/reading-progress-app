package com.example.grclone.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

// on signup, don't want to include user's hashed password as response on successful POST
@Data
@AllArgsConstructor
public class UserResponseDto {
    private String username;
    private String email;
    private String role;
}
