package com.akeel.library.dto;

import com.akeel.library.model.Book;
import com.akeel.library.model.Patron;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingRecordDto {

    private Long id;

    @NotNull(message = "Book is mandatory")
    private Book book;

    @NotNull(message = "Patron is mandatory")
    private Patron patron;


    @NotNull(message = "Borrow date is mandatory")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate borrowDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate returnDate;

    public BorrowingRecordDto setId(Long id) {
        this.id = id;
        return this;
    }

    public BorrowingRecordDto setBook(Book book) {
        this.book = book;
        return this;
    }

    public BorrowingRecordDto setPatron(Patron patron) {
        this.patron = patron;
        return this;
    }

    public BorrowingRecordDto setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
        return this;
    }

    public BorrowingRecordDto setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        return this;
    }
}
