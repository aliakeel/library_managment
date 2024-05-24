package com.akeel.library.controller.api;

import com.akeel.library.dto.PatronDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Tag(name = "Patron Management", description = "APIs related to patrons in the library")
public interface PatronApi {
    @Operation(summary = "Retrieve a list of patrons with pagination and search")
    public ResponseEntity<Page<PatronDto>> getAllPatrons(
            @RequestParam Optional<String> name,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size);


    @Operation(summary = "Retrieve all patrons")
    public ResponseEntity<List<PatronDto>> getAllPatrons();


    @Operation(summary = "Retrieve a specific patron by ID")
    public ResponseEntity<PatronDto> getPatronById(@PathVariable Long id);

    @Operation(summary = "Create a new patron")
    public ResponseEntity<PatronDto> createPatron(@Valid @RequestBody PatronDto patron);

    @Operation(summary = "Update an existing patron")
    public ResponseEntity<PatronDto> updatePatron(@PathVariable Long id, @Valid @RequestBody PatronDto patronDetails);

    @Operation(summary = "Delete a patron")
    public ResponseEntity<Object> deletePatron(@PathVariable Long id) ;

}
