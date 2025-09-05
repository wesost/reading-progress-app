package com.example.grclone.entities;


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
    @JoinColumn(name = "books_isbn", nullable = false)
    private Book book;

}
