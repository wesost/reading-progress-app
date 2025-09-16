package com.example.grclone.services;

import org.springframework.stereotype.Service;

import com.example.grclone.dtos.ReviewDto;
import com.example.grclone.repositories.UserRepository;
import com.example.grclone.repositories.BookRepository;
import com.example.grclone.mappers.ReviewMapper;
import com.example.grclone.repositories.ReviewRespository;
import com.example.grclone.entities.Review;
import com.example.grclone.entities.Book;
import com.example.grclone.entities.User;


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
    // METHODS
    // Optional<List<Review>> getReviewsForBooksByAuthor(String authorName) // authors w/ same name?
    // Optional<List<Review> getReviewsForBook(String isbn)
    // Optional<List<Review>> getReviewsByUserAndBookIsbn(String username, String isbn)
    // ReviewDto createReview(ReveiwDto)
    // deleteReview
    // updateReview
    // List<Review> getAllReviews() // paginate eventually
    
}
