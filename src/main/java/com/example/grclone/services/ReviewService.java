package com.example.grclone.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;


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

    public List<ReviewWithBookTitleDto> getAllReviews() {
        return reviewMapper.toListOfReviewWithBookTitleDto(reviewRespository.findAll());
    }

    public List<ReviewWithBookTitleDto> getAllUserReviews(String username){
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            
        List<Review> reviews = reviewRespository.findByReviewer(user);
        return reviewMapper.toListOfReviewWithBookTitleDto(reviews);
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

    
    // METHODS
    // Optional<List<Review>> getReviewsForBooksByAuthor(String authorName) // authors w/ same name?
    // Optional<List<Review> getReviewsForBook(String isbn)
    // Optional<List<Review>> getReviewsByUserAndBookIsbn(String username, String isbn)
    // ReviewDto createReview(ReveiwDto)
    // deleteReview
    // updateReview
    // List<Review> getAllReviews() // paginate eventually
    
}
