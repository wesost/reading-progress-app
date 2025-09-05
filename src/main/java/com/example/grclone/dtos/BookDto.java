package com.example.grclone.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// bookdto is what is exposed by api, might not want to include everything 
// stored for all books (ex. internal ids, date added to db, etc) in response so use DTO.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private String isbn;
    private String title;
    private String authorName;

}
