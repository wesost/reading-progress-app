package com.example.grclone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grclone.entities.Book;

public interface BookRepository extends JpaRepository<Book, String> {

}