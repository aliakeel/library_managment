package com.akeel.library.data;

import com.akeel.library.dto.BookDto;
import com.akeel.library.dto.BorrowingRecordDto;
import com.akeel.library.dto.PatronDto;
import com.akeel.library.model.Book;
import com.akeel.library.model.BorrowingRecord;
import com.akeel.library.model.Patron;

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.akeel.library.mapping.Mapping.toBookDto;
import static org.mockito.Mockito.mock;

public class TestData {
    public static Book book(){
        return Book.builder()
                .title("java how to program")
                .author("ali")
                .isbn("123456")
                .publicationYear(2024)
                .build();
    }

    public static Book bookDto(){
        return new Book()
                .setTitle("java how to program")
                .setAuthor("ali")
                .setIsbn("123456")
                .setPublicationYear(2024);
    }

    public static List<Book> books(){
        List list = mock(List.class);
        for (int i = 0; i < 5; i++) {
            list.add(
                    new Book()
                            .setTitle("library managements" + i)
                            .setAuthor("ali")
                            .setIsbn("12345"+i)
                            .setPublicationYear(2024));
        }
        return list;
    }

    public static List<BookDto> booksDto(){
        return new ArrayList<>(
                Arrays.asList(
                        new BookDto(1L, "Spring Boot @WebMvcTest", "ali",2021,"dsvfgdfvsfg"),
                        new BookDto(2L, "Spring Boot Web MVC", "momo",2021,"dsvffasfgfg"),
                        new BookDto(3L, "How to program with java", "D&D",2021,"dsvgdf"),
                        new BookDto(4L, "Spring Boot WebMvcTest 4", "koko",2021,"dsvdfbgdfbfgfg")));

    }

    public static Patron patron(){
        return Patron.builder()
                .name("ali")
                .contactInfo("ali@g.com")
                .build();
    }
    public static PatronDto patronDto(){
        return new PatronDto()
                .setName("ali")
                .setContactInfo("ali@g.com");
    }

    public static List<PatronDto> patronsDto(){
        return new ArrayList<>(
                Arrays.asList(
                        new PatronDto(1L, "ali","ali@g1.com"),
                        new PatronDto(2L, "ali","ali@g2.com"),
                        new PatronDto(3L, "ali","ali@g3.com"),
                        new PatronDto(4L, "ali","ali@g4.com")));

    }

    public static BorrowingRecord borrowingRecord(){
        return BorrowingRecord.builder()
                .id(1L)
                .borrowDate(LocalDate.now())
                .returnDate(null)
                .book(book())
                .patron(patron())
                .build();
    }
    public static BorrowingRecordDto borrowingRecordDto(){
        return new BorrowingRecordDto()
                .setPatron(patron())
                .setBook(book())
                .setId(1L)
                .setBorrowDate(LocalDate.now())
                .setReturnDate(LocalDate.now());
    }

    public static List<BorrowingRecordDto> borrowingRecordsDto(){
        return new ArrayList<>(
                Arrays.asList(
                        new BorrowingRecordDto(1L, book(),patron(),LocalDate.now(),LocalDate.now()),
                        new BorrowingRecordDto(2L, book(),patron(),LocalDate.now(),LocalDate.now()),
                        new BorrowingRecordDto(3L, book(),patron(),LocalDate.now(),LocalDate.now()),
                        new BorrowingRecordDto(4L, book(),patron(),LocalDate.now(),LocalDate.now()),
                        new BorrowingRecordDto(5L, book(),patron(),LocalDate.now(),LocalDate.now())
                ));

    }
}
