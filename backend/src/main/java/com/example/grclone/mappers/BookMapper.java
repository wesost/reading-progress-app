// manual bookmapper but could also use ModelMapper or MapStruct in the future
package com.example.grclone.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.grclone.dtos.BookDto;
import com.example.grclone.entities.Author;
import com.example.grclone.entities.Book;

@Component
public class BookMapper {
    public BookDto toDto(Book book) {
        return new BookDto(
            book.getIsbn(),
            book.getTitle(),
            book.getImageUrl(),
            book.getAuthor().getName());
    }

    public Book toEntity(BookDto dto, Author author) {
        return new Book(
            dto.getIsbn(),
            dto.getTitle(),
            author,
            dto.getImageUrl(),
            new ArrayList<>()

            );
    }

    // maps list of Book to list of BookDtos
    public List<BookDto> toDtoList(List<Book> books) {
        return books.stream()
        .map(this::toDto)
        .collect(Collectors.toList());
    }
}
