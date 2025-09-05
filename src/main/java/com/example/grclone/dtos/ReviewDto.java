package com.example.grclone.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long id;
    private String reviewerUsername;
    private String bookIsbn;
    private int rating;
    private String reviewText;
}
