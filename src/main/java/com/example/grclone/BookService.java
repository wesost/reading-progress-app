package com.example.grclone;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDto getBookByIsbn(String isbn) {
        Book book = bookRepository.findById(isbn)
        .orElseThrow(() -> new RuntimeException("Book not found: " + isbn));
        return BookMapper.toDto(book);
    }

     public List<BookDto> getAllBooks() {
        return BookMapper.toDtoList(bookRepository.findAll());
    }


}
