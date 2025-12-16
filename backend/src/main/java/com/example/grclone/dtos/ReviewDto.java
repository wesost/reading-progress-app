package com.example.grclone.dtos;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private String reviewerUsername;
    private String bookIsbn;

    @DecimalMin("0.00")
    @DecimalMax("10.00")
    private Float rating;
    private String reviewText;

    @PastOrPresent
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate dateFinished;
}
