package com.example.grclone.entities;


import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.DecimalMax;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reviews")
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin("0.00")
    @DecimalMax("10.00")
    private Float rating; // restrict the range this can be in eventually

    private String reviewText;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "book_isbn", nullable = false)
    private Book book;

    @PastOrPresent
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate dateFinished;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Review(Float rating, String reviewText, User reviewer, Book book, LocalDate dateFinished) {
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewer = reviewer;
        this.book = book;
        this.dateFinished = dateFinished;
    }
}
