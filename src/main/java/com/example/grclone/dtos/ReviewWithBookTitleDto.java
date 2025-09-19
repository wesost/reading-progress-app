package com.example.grclone.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewWithBookTitleDto {
    private Long id;
    private String reviewerUsername;
    private String bookIsbn;
    private String bookTitle;
    private Float rating;
    private String reviewText;
    private LocalDateTime createdAt;

    
}
