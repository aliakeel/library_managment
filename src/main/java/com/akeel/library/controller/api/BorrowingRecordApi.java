package com.akeel.library.controller.api;


import com.akeel.library.dto.BorrowingRecordDto;
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
@Tag(name = "Borrowing Record Management", description = "APIs related to borrowing records in the library")
public interface BorrowingRecordApi {

    @Operation(
            summary = "Fetch all Borrowing Records",
            description = "fetches all Borrowing Records entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    ResponseEntity<List<BorrowingRecordDto>> getAllBorrowingRecords();

    @Operation(summary = "Retrieve a list of borrowing records with pagination and search")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    ResponseEntity<List<BorrowingRecordDto>> getBorrowingRecordsWithFilter(
            @RequestParam Optional<String> title,
            @RequestParam Optional<String> author,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size);


    @Operation(summary = "Retrieve a specific borrowing record by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    public ResponseEntity<BorrowingRecordDto> getBorrowingRecordById(@PathVariable Long id);

    @Operation(summary = "Create a new borrowing record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully added a book"),
            @ApiResponse(responseCode = "403", description = "not authorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "409", description = "duplicate book"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    ResponseEntity<BorrowingRecordDto> createBorrowingRecord(@RequestBody BorrowingRecordDto book);

    @Operation(summary = "Update an existing borrowing record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "403", description = "not authorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    ResponseEntity<BorrowingRecordDto> updateBorrowingRecord(@PathVariable Long id, @RequestBody BorrowingRecordDto bookDetails);

    @Operation(summary = "Allow a patron to borrow a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "403", description = "not authorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    ResponseEntity<BorrowingRecordDto> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId);

    @Operation(summary = "Record the return of a borrowed book by a patron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "403", description = "not authorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    ResponseEntity<BorrowingRecordDto> returnBook(@PathVariable Long bookId, @PathVariable Long patronId);

    @Operation(summary = "Delete a borrowing record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully deleted a book"),
            @ApiResponse(responseCode = "403", description = "not authorized"),
            @ApiResponse(responseCode = "404", description = "book with requested id not found")
    })
    ResponseEntity<Object> deleteBorrowingRecord(@PathVariable Long id);

}
