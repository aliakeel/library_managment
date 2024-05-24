package com.akeel.library.controller.api;


import com.akeel.library.dto.BookDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Tag(name = "Book Management", description = "APIs related to Books in the library")
public interface BookApi {

    @Operation(
            summary = "Fetch all books",
            description = "fetches all book entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    ResponseEntity<List<BookDto>> getAllBooks();


    @Operation(
            summary = "Fetch all books available to borrow",
            description = "fetches all book entities available to borrow and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    ResponseEntity<List<BookDto>> getAllAvailableBooks();

    @Operation(
            summary = "Fetch books with optional search",
            description = "" +
                    "fetches all book entities and their data from data source\n" +
                    "with optional search using (title, author)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    ResponseEntity<List<BookDto>> getBooksWithFilter(
            @RequestParam Optional<String> title,
            @RequestParam Optional<String> author,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size);


    @Operation(
            summary = "Fetch book with presented id",
            description = "" +
                    "fetches book entity that has the requested id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id);

    @Operation(
            summary = "Create a book",
            description = "" +
                    "Create a book entity and add it to data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully added a book"),
            @ApiResponse(responseCode = "403", description = "not authorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "409", description = "duplicate book"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    ResponseEntity<BookDto> createBook(@RequestBody BookDto book);

    @Operation(
            summary = "Update a book",
            description = "" +
                    "Update a book entity and make the changes to data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "403", description = "not authorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto bookDetails);
    @Operation(
            summary = "delete a book",
            description = "delete a book using the id of the book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully deleted a book"),
            @ApiResponse(responseCode = "403", description = "not authorized"),
            @ApiResponse(responseCode = "404", description = "book with requested id not found")
    })
    ResponseEntity<Object> deleteBook(@PathVariable Long id);

}
