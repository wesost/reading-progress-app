package com.example.grclone.mappers;

import org.springframework.stereotype.Component;

import com.example.grclone.dtos.ReviewDto;
import com.example.grclone.dtos.ReviewWithBookTitleDto;
import com.example.grclone.entities.Review;
import com.example.grclone.entities.Book;
import com.example.grclone.entities.User;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<ReviewDto> toDtoList(List<Review> reviews) { // currently unused in favor of toListOfReviewWithBookTitleDto
        return reviews.stream()
        .map(this::toDto)
        .collect(Collectors.toList());
    }

    public ReviewWithBookTitleDto toReviewWithBookTitleDto(Review review) {
        return new ReviewWithBookTitleDto(
            review.getReviewer().getUsername(),
            review.getBook().getIsbn(),
            review.getBook().getTitle(),
            review.getRating(),
            review.getReviewText()
        );
    }

    public List<ReviewWithBookTitleDto> toListOfReviewWithBookTitleDto(List<Review> reviews) {
        return reviews.stream()
        .map(this::toReviewWithBookTitleDto)
        .collect(Collectors.toList());
    }

}
