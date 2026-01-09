package com.example.grclone.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthUserDto {
    private Long id;
    private String username;
    private String role;
}
