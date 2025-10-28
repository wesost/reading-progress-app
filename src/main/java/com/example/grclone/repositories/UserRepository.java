package com.example.grclone.repositories;

import com.example.grclone.entities.User;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
    void deleteByVerifiedFalseAndCreatedAtBefore(LocalDateTime cutoff);

} 
