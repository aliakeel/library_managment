package com.akeel.library.controller;

import com.akeel.library.dto.BorrowingRecordDto;
import com.akeel.library.mapping.BorrowingRecordMapper;
import com.akeel.library.service.BorrowingRecordService;
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

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.akeel.library.data.TestData.*;
import static com.akeel.library.mapping.Mapping.toBorrowingRecordDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BorrowingRecordControllerTest {


    MockMvc mockMvc;
    ObjectMapper objectMapper;
    BorrowingRecordService borrowingRecordService;

    @BeforeEach
    void setUp() {
        borrowingRecordService = mock(BorrowingRecordService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new BorrowingRecordController(borrowingRecordService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getBorrowingRecordsWithFilter() throws Exception {

        String name = "ali";
        String title_book = "java";
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("patronName", name);
        paramsMap.add("bookTitle", title_book);
        List list =borrowingRecordsDto();
        List ali_borrowingRecords = borrowingRecordsDto().stream().filter(
                borrowingRecordDto -> borrowingRecordDto.getPatron().getName().contains(name)
                        || borrowingRecordDto.getBook().getTitle().contains(title_book)
        ).toList();
        Pageable pageable = PageRequest.of(0, 10);
        Page<BorrowingRecordDto> borrowingRecordDtoPage = new PageImpl<>(ali_borrowingRecords, pageable, ali_borrowingRecords.size());
        when(borrowingRecordService.findByPatronNameContainingOrBookTitleContaining(name,title_book, pageable)).thenReturn(borrowingRecordDtoPage);
        mockMvc.perform(get("/api/borrowings").params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(ali_borrowingRecords.size()))
                .andDo(print());
    }

    @Test
    void getAllBorrowingRecords() throws Exception {
        when(borrowingRecordService.findAll()).thenReturn(borrowingRecordsDto());
        mockMvc.perform(get("/api/borrowings/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(borrowingRecordsDto().size()))
                .andDo(print());
    }

    @Test
    void getBorrowingRecordById() throws Exception {
        long id = 1L;
        BorrowingRecordDto borrowingRecordDto = new BorrowingRecordDto(1L, book(),patron(), LocalDate.now(),LocalDate.now());

        when(borrowingRecordService.findById(id)).thenReturn(Optional.of(borrowingRecordDto));
        mockMvc.perform(get("/api/borrowings/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.patron.name").value(borrowingRecordDto.getPatron().getName()))
                .andExpect(jsonPath("$.book.title").value(borrowingRecordDto.getBook().getTitle()))
                .andDo(print());
    }

    @Test
    void shouldReturnNotFoundBorrowingRecord() throws Exception {
        long id = 1L;

        when(borrowingRecordService.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/borrowings/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void borrowBook() throws Exception {
        when(borrowingRecordService.borrowBook(any(),any())).thenReturn(BorrowingRecordMapper.INSTANCE.BorrowingRecordToBorrowingRecordDto(borrowingRecord()).setId(1L));
        mockMvc.perform(post("/api/borrowings/borrow/{bookId}/patron/{patronId}",5,5))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.book.title").value(toBorrowingRecordDto(borrowingRecord()).setId(1L).getBook().getTitle()))
                .andExpect(content().json(objectMapper.writeValueAsString(toBorrowingRecordDto(borrowingRecord()).setId(1L))))
                .andDo(print());
    }

    @Test
    void updateBorrowingRecord() throws Exception {
        long id = 1L;

        BorrowingRecordDto borrowingRecordDto = new BorrowingRecordDto(1L, book(),patron(),LocalDate.now(),LocalDate.now());
        BorrowingRecordDto updatedborrowingRecordDto = new BorrowingRecordDto(1L, book().setTitle("updated"),patron().setName("updated"),LocalDate.now(),LocalDate.now());


        when(borrowingRecordService.findById(id)).thenReturn(Optional.of(borrowingRecordDto));
        when(borrowingRecordService.update(any(), any(BorrowingRecordDto.class))).thenReturn(Optional.of(updatedborrowingRecordDto));

        mockMvc.perform(put("/api/borrowings/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedborrowingRecordDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patron.name").value(updatedborrowingRecordDto.getPatron().getName()))
                .andExpect(jsonPath("$.book.title").value(updatedborrowingRecordDto.getBook().getTitle()))
                .andDo(print());
    }

    @Test
    void deleteBorrowingRecord() throws Exception {
        long id = 1L;

        when(borrowingRecordService.deleteById(any())).thenReturn(true);
        mockMvc.perform(delete("/api/borrowings/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }
}