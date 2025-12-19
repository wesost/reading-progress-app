package com.example.grclone.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.grclone.services.ReviewService;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.grclone.dtos.HomeResponseDto;
import com.example.grclone.dtos.ReviewWithBookTitleDto;


@RestController
@RequestMapping("/api")
public class HomeController {
    
    private final ReviewService reviewService;

    public HomeController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/home")
    public HomeResponseDto getLandingPageData(Authentication authentication) {
        List<ReviewWithBookTitleDto> recentReviews = reviewService.getRecentReviews();
        int totalReviews = reviewService.getReviewCount();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new HomeResponseDto(
                false,
                null,
                "Welcome to the Site",
                recentReviews,
                totalReviews
            );
        }

        String username = authentication.getName();
        return new HomeResponseDto(true, username,
        "Welcome back to the Site, "+ username + ".",
        recentReviews,
        totalReviews
        );
    }
    
}
