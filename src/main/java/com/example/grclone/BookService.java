package com.example.grclone;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.grclone.entities.Author;
import com.example.grclone.entities.Book;
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
        .orElseThrow(() -> new RuntimeException("Author not found: " +bookDto.getAuthorName()));

        Book book = bookMapper.toEntity(bookDto, author);
        Book saved = bookRepository.save(book);
        return bookMapper.toDto(saved);
    } 

     public List<BookDto> getAllBooks() {
        return bookMapper.toDtoList(bookRepository.findAll());
    }
}
