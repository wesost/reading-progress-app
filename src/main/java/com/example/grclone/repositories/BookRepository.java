package com.example.grclone.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grclone.entities.Book;

public interface BookRepository extends JpaRepository<Book, String> {

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Book> findByAuthor_NameContainingIgnoreCase(String author, Pageable pageable); // _ steps into Author object and finds Name from there
}