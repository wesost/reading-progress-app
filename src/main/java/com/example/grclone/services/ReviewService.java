package com.example.grclone.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public ReviewDto createReview(ReviewDto reviewDto) {

        Book book = bookRepository.findById(reviewDto.getBookIsbn())
        .orElseThrow(() -> new RuntimeException("Book not found"));

        User user = userRepository.findByUsername(reviewDto.getReviewerUsername())
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
        String username = principal.getName();

        Review review = reviewRespository.findById(reviewId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));


        if (!review.getReviewer().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized review deletion attempt");
        }

        reviewRespository.delete(review);
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
