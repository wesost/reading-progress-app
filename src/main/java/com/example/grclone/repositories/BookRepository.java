package com.example.grclone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grclone.entities.Book;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    // search by title
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthor_NameContainingIgnoreCase(String author); // _ steps into Author object and finds Name from there
}