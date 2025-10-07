package com.example.grclone.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import com.example.grclone.dtos.BookDto;
import com.example.grclone.services.BookService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
@RequestMapping("/books")
public class BookController {
    
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Page<BookDto> getAllBooks(
        @PageableDefault(sort = "title") Pageable pageable
    ) {
        return bookService.getAllBooks(pageable);
    }
    

    @GetMapping("/{isbn}")
    public BookDto getBookByIsbn(@PathVariable("isbn") String isbn) {
        return bookService.getBookByIsbn(isbn);
    }

    @PostMapping // create a new book
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto created = bookService.createBook(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookDto>> searchBooks(
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(value = "author", required = false) String author,
        @PageableDefault(size = 10, sort = "title") Pageable pageable
        ) {
        Page<BookDto> results = bookService.searchBooks(title, author, pageable);
        return ResponseEntity.ok(results);
    }
    
}
