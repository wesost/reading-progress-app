package com.example.grclone.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private String reviewerUsername;
    private String bookIsbn;
    private Float rating;
    private String reviewText;
}
