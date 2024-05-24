package com.akeel.library.controller;

import com.akeel.library.data.TestData;
import com.akeel.library.dto.BookDto;
import com.akeel.library.service.BookService;
import com.akeel.library.mapping.BookMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.akeel.library.data.TestData.booksDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BooksControllerTest {

    MockMvc mockMvc;
    ObjectMapper objectMapper;
    BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = mock(BookService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new BookController(bookService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getBooksWithFilter() throws Exception {

        String title = "java";
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("title", title);
        List java_books = booksDto().stream().filter(bookDto -> bookDto.getTitle().contains(title)).toList();
        Pageable pageable = PageRequest.of(0, 10);
        Page<BookDto> bookDtoPage = new PageImpl<>(java_books, pageable, java_books.size());
        when(bookService.findByTitleContainingOrAuthorContaining(title,"", pageable)).thenReturn(bookDtoPage);
        mockMvc.perform(get("/api/books").params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andDo(print());
    }

    @Test
    void getAllBooks() throws Exception {
        when(bookService.findAll()).thenReturn(booksDto());
        mockMvc.perform(get("/api/books/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(booksDto().size()))
                .andDo(print());
    }

    @Test
    void getBookById() throws Exception {
        long id = 1L;
        BookDto bookDto = new BookDto(1L, "java how to program", "D&D",2010,"dsvfgdfvsfg");

        when(bookService.findById(id)).thenReturn(Optional.of(bookDto));
        mockMvc.perform(get("/api/books/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(bookDto.getTitle()))
                .andExpect(jsonPath("$.author").value(bookDto.getAuthor()))
                .andExpect(jsonPath("$.publicationYear").value(bookDto.getPublicationYear()))
                .andExpect(jsonPath("$.isbn").value(bookDto.getIsbn()))
                .andDo(print());
    }

    @Test
    void shouldReturnNotFoundBook() throws Exception {
        long id = 1L;

        when(bookService.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/books/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void createBook() throws Exception{
        when(bookService.save(any())).thenReturn(BookMapper.INSTANCE.BookToBookDto(TestData.book().setId(1L)));
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.bookDto())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(TestData.bookDto().setId(1L))))
        .andDo(print());
    }

    @Test
    void updateBook() throws Exception {
        long id = 1L;

        BookDto bookDto = new BookDto(id, "Spring Boot @WebMvcTest", "ali",2021, "#2454225");
        BookDto updatedbookDto = new BookDto(id, "Updated", "ali",2021, "#2454225");


        when(bookService.findById(id)).thenReturn(Optional.of(bookDto));
        when(bookService.update(any(),any(BookDto.class))).thenReturn(Optional.of(updatedbookDto));

        mockMvc.perform(put("/api/books/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedbookDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatedbookDto.getTitle()))
                .andExpect(jsonPath("$.author").value(updatedbookDto.getAuthor()))
                .andExpect(jsonPath("$.publicationYear").value(updatedbookDto.getPublicationYear()))
                .andExpect(jsonPath("$.isbn").value(updatedbookDto.getIsbn()))
                .andDo(print());
    }

    @Test
    void deleteBook() throws Exception {
        long id = 1L;

        when(bookService.deleteById(any())).thenReturn(true);
        mockMvc.perform(delete("/api/books/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }
}