package com.example.grclone.repositories;
import com.example.grclone.entities.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.grclone.entities.User;


public interface ReviewRespository extends JpaRepository<Review, Long> {

    List<Review> findByReviewer(User reviewer);

}