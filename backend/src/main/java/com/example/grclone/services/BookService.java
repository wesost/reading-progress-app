package com.example.grclone.services;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.grclone.entities.User;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import com.example.grclone.dtos.BookDto;
import com.example.grclone.entities.Author;
import com.example.grclone.entities.Book;
import com.example.grclone.mappers.BookMapper;
import com.example.grclone.repositories.AuthorRepository;
import com.example.grclone.repositories.BookRepository;
import com.example.grclone.repositories.UserRepository;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
        this.userRepository = userRepository;
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

    public void deleteBook(String isbn, Principal principal){
        Book book = bookRepository.findById(isbn)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Book to delete not found"
        ));

        User currentUser = userRepository.findByUsername(principal.getName())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.UNAUTHORIZED,
            "User not found"
        ));

        if (currentUser.getRole().equals("ROLE_ADMIN")) {
            bookRepository.delete(book);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Error deleting book");
        }
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
