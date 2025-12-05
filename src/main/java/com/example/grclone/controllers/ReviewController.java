package com.example.grclone.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.example.grclone.services.ReviewService;
import com.example.grclone.dtos.ReviewDto;
import com.example.grclone.dtos.ReviewWithBookTitleDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/books/{isbn}")
    public ResponseEntity<ReviewDto> createReview(
        @Valid @RequestBody ReviewDto reviewDto,
        Principal principal
    ) {
        ReviewDto created = reviewService.createReview(reviewDto, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping()
    public Page<ReviewWithBookTitleDto> getAllReviews(
        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
        Pageable pageable
    ) {
        return reviewService.getAllReviews(pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id, Principal principal) {
        reviewService.deleteReview(id, principal);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}")
    public Page<ReviewWithBookTitleDto> getAllUserReviews(
        @PathVariable String username,
        @PageableDefault(sort = "reviewerUsername")
        Pageable pageable) {
        return reviewService.getAllUserReviews(username, pageable);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReviewDto> updateReview(
        @PathVariable Long id,
        @RequestBody ReviewDto updatedReviewDto,
        Authentication authentication
    ) {
        ReviewDto updated = reviewService.updateReview(id, updatedReviewDto, authentication);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/recent")
    public List<ReviewWithBookTitleDto> getRecentReviews() {
        return reviewService.getRecentReviews();
    }
    
}
