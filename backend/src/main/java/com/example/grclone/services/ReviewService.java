package com.example.grclone.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.example.grclone.dtos.ReviewDto;
import com.example.grclone.dtos.ReviewWithBookTitleDto;
import com.example.grclone.repositories.UserRepository;
import com.example.grclone.repositories.BookRepository;
import com.example.grclone.mappers.ReviewMapper;
import com.example.grclone.repositories.ReviewRespository;
import com.example.grclone.entities.Review;
import com.example.grclone.entities.Book;
import com.example.grclone.entities.User;

import java.security.Principal;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRespository reviewRespository;
    private final ReviewMapper reviewMapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;


    public ReviewService(ReviewRespository reviewRespository, ReviewMapper reviewMapper, BookRepository bookRepository, UserRepository userRepository) {
        this.reviewRespository = reviewRespository;
        this.reviewMapper = reviewMapper;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public ReviewDto createReview(ReviewDto reviewDto, Principal principal) {

        Book book = bookRepository.findById(reviewDto.getBookIsbn())
        .orElseThrow(() -> new RuntimeException("Book not found"));

        User user = userRepository.findByUsername(principal.getName())
        .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = reviewMapper.toEntity(reviewDto, user, book);
        Review saved = reviewRespository.save(review);
        return reviewMapper.toDto(saved);
    }

    public Page<ReviewWithBookTitleDto> getAllReviews(Pageable pageable) {
        Page<Review> page = reviewRespository.findAll(pageable);
        return page.map(reviewMapper::toReviewWithBookTitleDto);
    }

    public Page<ReviewWithBookTitleDto> getAllUserReviews(String username, Pageable pageable){
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            
        Page<Review> results = reviewRespository.findByReviewer(user, pageable);
        return results.map(reviewMapper::toReviewWithBookTitleDto);
    } 

    public void deleteReview(Long reviewId, Principal principal) {

        Review review = reviewRespository.findById(reviewId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Review not found"
            ));
            
        User currentUser = userRepository.findByUsername(principal.getName())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "User not found"
            ));

        if (currentUser.getRole().equals("ROLE_ADMIN") || review.getReviewer().getUsername().equals(currentUser.getUsername())) {
            reviewRespository.delete(review);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot delete this review");
        }
    }


    public ReviewDto updateReview(Long id, ReviewDto reviewDto, Authentication authentication) {
        Review review = reviewRespository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review not found"));
            
        String currentUsername = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if(!isAdmin && !review.getReviewer().getUsername().equals(currentUsername)) {
            throw new RuntimeException("You are not allowed to update this review");
        }

        if (reviewDto.getReviewText() != null) {
            review.setReviewText(reviewDto.getReviewText());
        }
        if (reviewDto.getRating() != null) {
            review.setRating(reviewDto.getRating());
        }

        Review saved = reviewRespository.save(review);
        return reviewMapper.toDto(saved);
    }

    public List<ReviewWithBookTitleDto> getRecentReviews() {
        List<Review> reviews = reviewRespository.findTop3ByOrderByCreatedAtDesc();
        return reviews.stream()
            .map(reviewMapper::toReviewWithBookTitleDto)
            .toList();
    }

    public int getReviewCount() {
        return (int) reviewRespository.count();
    }
    
}
