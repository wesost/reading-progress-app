package com.example.grclone.entities;


import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

import jakarta.persistence.*;
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

    private Float rating; // restrict the range this can be in eventually

    private String reviewText;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "book_isbn", nullable = false)
    private Book book;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Review(Float rating, String reviewText, User reviewer, Book book) {
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewer = reviewer;
        this.book = book;
    }
}
