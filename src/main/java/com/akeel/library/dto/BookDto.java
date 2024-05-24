package com.akeel.library.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Size(max = 200, message = "Title should not exceed 200 characters")
    private String title;

    @NotBlank(message = "Author is mandatory")
    @Size(max = 100, message = "Author should not exceed 200 characters")
    private String author;

    @NotBlank(message = "publicationYear is mandatory")
    private int publicationYear;

    @NotBlank(message = "Isbn is mandatory")
    @Size(min=10,max = 10, message = "Author should not exceed 200 characters")
    private String isbn;

}
