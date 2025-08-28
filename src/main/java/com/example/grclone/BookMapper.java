// manual bookmapper but could also use ModelMapper or MapStruct in the future
package com.example.grclone;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {
    public static BookDto toDto(Book book) {
        return new BookDto(book.getIsbn(), book.getTitle(), book.getAuthor());
    }

    public static Book toEntity(BookDto dto) {
        return new Book(dto.getIsbn(), dto.getTitle(), dto.getAuthor());
    }

    // maps list of Book to list of BookDtos
    public static List<BookDto> toDtoList(List<Book> books) {
        return books.stream()
        .map(BookMapper::toDto)
        .collect(Collectors.toList());
    }
}
