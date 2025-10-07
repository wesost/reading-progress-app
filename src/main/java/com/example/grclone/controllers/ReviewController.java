package com.example.grclone.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.example.grclone.services.ReviewService;
import com.example.grclone.dtos.ReviewDto;
import com.example.grclone.dtos.ReviewWithBookTitleDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/books/{isbn}")
    public ResponseEntity<ReviewDto> createReview(
        @RequestBody ReviewDto reviewDto,
        Principal principal
    ) {
        ReviewDto created = reviewService.createReview(reviewDto, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping()
    public List<ReviewWithBookTitleDto> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id, Principal principal) {
        reviewService.deleteReview(id, principal);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}")
    public List<ReviewWithBookTitleDto> getAllUserReviews(@PathVariable String username) {
        return reviewService.getAllUserReviews(username);
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
}
