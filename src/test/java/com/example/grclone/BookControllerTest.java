package com.example.grclone;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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


}
