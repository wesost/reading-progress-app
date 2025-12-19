package com.example.grclone.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HomeResponseDto {
    private boolean authenticated;
    private String username;
    private String landingPageMessage;
    private List<ReviewWithBookTitleDto> recentReviews;
    private int totalReviews;
}
