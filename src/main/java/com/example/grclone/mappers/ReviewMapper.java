package com.example.grclone.mappers;

import org.springframework.stereotype.Component;

import com.example.grclone.dtos.ReviewDto;
import com.example.grclone.entities.Review;
import com.example.grclone.entities.Book;
import com.example.grclone.entities.User;


@Component
public class ReviewMapper {

    public ReviewDto toDto(Review review){
        return new ReviewDto(
            review.getReviewer().getUsername(),
            review.getBook().getIsbn(),
            review.getRating(),
            review.getReviewText()
        );
    }

    public Review toEntity(ReviewDto dto, User user, Book book) {
        return new Review(
            dto.getRating().floatValue(),
            dto.getReviewText(),
            user,
            book
        );
    }
}
