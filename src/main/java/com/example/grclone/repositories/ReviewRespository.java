package com.example.grclone.repositories;
import com.example.grclone.entities.Review;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRespository extends JpaRepository<Review, Long> {

}