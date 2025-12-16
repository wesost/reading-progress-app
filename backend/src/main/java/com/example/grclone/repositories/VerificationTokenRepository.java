package com.example.grclone.repositories;

import com.example.grclone.entities.VerificationToken;
import java.time.LocalDateTime;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);
    void deleteByExpiryDateBefore(LocalDateTime now);
    
}