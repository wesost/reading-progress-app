package com.example.grclone.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import com.example.grclone.dtos.BookDto;
import com.example.grclone.entities.Author;
import com.example.grclone.entities.Book;
import com.example.grclone.mappers.BookMapper;
import com.example.grclone.repositories.AuthorRepository;
import com.example.grclone.repositories.BookRepository;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
    }

    public BookDto getBookByIsbn(String isbn) {
        Book book = bookRepository.findById(isbn)
        .orElseThrow(() -> new RuntimeException("Book not found: " + isbn));
        return bookMapper.toDto(book);
    }

    public BookDto createBook(BookDto bookDto) {
        Author author = authorRepository.findByName(bookDto.getAuthorName())
        .orElseGet(() -> {
            // if author doesn't already exist, create a new one
            Author newAuthor = new Author(bookDto.getAuthorName());
            return authorRepository.save(newAuthor);
            
        });

        Book book = bookMapper.toEntity(bookDto, author);
        Book saved = bookRepository.save(book);
        return bookMapper.toDto(saved);
    } 

     public Page<BookDto> getAllBooks(Pageable pageable) {
        Page<Book> page = bookRepository.findAll(pageable);
        return page.map(bookMapper::toDto);
    }

    public Page<BookDto> searchBooks(String title, String author, Pageable pageable) {
        if ((title == null || title.isBlank()) &&
        (author == null || author.isBlank())) {
            throw new IllegalArgumentException("At least one search parameter must be entered");
        }
        Set<Book> results = new HashSet<>();

        if (title != null && !title.isBlank()) {
            results.addAll(bookRepository.findByTitleContainingIgnoreCase(title, pageable).getContent());
        }

        if (author != null && !author.isBlank()) {
            results.addAll(bookRepository.findByAuthor_NameContainingIgnoreCase(author, pageable).getContent());
        }

        List<BookDto> dtoList = results.stream()
            .map(bookMapper::toDto)
            .toList();

        return new PageImpl<>(dtoList, pageable, dtoList.size());
    }
}
