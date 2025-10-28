package com.example.grclone.entities;

import lombok.*;

import java.time.LocalDateTime;

import jakarta.persistence.*;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "verificationTokens")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user; // don't map to user but maybe their id

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    

    
    
}
