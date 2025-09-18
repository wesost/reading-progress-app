package com.example.grclone.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewWithBookTitleDto {
    private String reviewerUsername;
    private String bookIsbn;
    private String bookTitle;
    private Float rating;
    private String reviewText;

    
}
