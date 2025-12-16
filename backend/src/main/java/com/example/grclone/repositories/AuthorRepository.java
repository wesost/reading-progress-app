package com.example.grclone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grclone.entities.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long>{
    
    // spring auto generates a query like SELECT * FROM authors WHERE name = ?;
    Optional<Author> findByName(String name);
}
