package com.example.grclone;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.grclone.controllers.BookController;
import com.example.grclone.dtos.BookDto;
import com.example.grclone.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import  org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // replace service layer w/ mock
    private BookService bookService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetBookByIsbnReturnsBookDto() throws Exception {

        BookDto mockBook = new BookDto("1231231231233", "Test single book", "fake author");
        // 
        when(bookService.getBookByIsbn("1231231231233")).thenReturn(mockBook);

        //act/assert
        mockMvc.perform(get("/books/1231231231233")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.isbn").value("1231231231233"))
        .andExpect(jsonPath("$.title").value("Test single book"))
        .andExpect(jsonPath("$.authorName").value("fake author"));
    }



    @Test
    void testThatListBooksReturnsStatus200OkAndResponse() throws Exception {

        BookDto mockBook = new BookDto("1234567890", "Test Title", "Test Author");
        // if /books endpoint hit, return this list with the test book in it
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(mockBook));

        // simulate get request
        mockMvc.perform(get("/books").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].isbn").value("1234567890"))
               .andExpect(jsonPath("$[0].title").value("Test Title"))
               .andExpect(jsonPath("$[0].authorName").value("Test Author"));
    }


    // test /books correctly maps POST reqs to bookService.createBook
    // and that returned JSON matches expected dto
    @Test
    void testCreateBookEndpoint() throws Exception {
        BookDto inputDto = new BookDto("1234556789012", "testbook", "Fake author");
        BookDto savedDto = new BookDto("9781234567890", "Test Book", "Test Author");

        when(bookService.createBook(any(BookDto.class))).thenReturn(savedDto);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated()) // HTTP 201
                .andExpect(jsonPath("$.isbn").value("9781234567890"))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.authorName").value("Test Author"));
    }

}
