package com.example.grclone.services;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.grclone.repositories.UserRepository;
import com.example.grclone.repositories.VerificationTokenRepository;

@Service
public class CleanupService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    public CleanupService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
    }


    // runs cleanup of unverified users/verification tokens
    @Scheduled(cron = "0 0 0 * * *")
    public void cleanExpiredData() {
        LocalDateTime now = LocalDateTime.now();
        verificationTokenRepository.deleteByExpiryDateBefore(now);

        LocalDateTime cutoff = now.minusDays(1);
        userRepository.deleteByVerifiedFalseAndCreatedAtBefore(cutoff);

        System.out.println("Cleanup ran at "+ now);
    }
}
