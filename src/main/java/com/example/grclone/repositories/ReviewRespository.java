package com.example.grclone.repositories;
import com.example.grclone.entities.Review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.grclone.entities.User;



public interface ReviewRespository extends JpaRepository<Review, Long> {

    Page<Review> findByReviewer(User reviewer, Pageable pageable);

}