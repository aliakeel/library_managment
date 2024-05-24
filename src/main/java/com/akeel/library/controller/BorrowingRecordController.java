package com.akeel.library.controller;

import com.akeel.library.controller.api.BorrowingRecordApi;
import com.akeel.library.dto.BorrowingRecordDto;
import com.akeel.library.service.BorrowingRecordService;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor
@RequestMapping("/api/borrowings")
@Validated
public class BorrowingRecordController implements BorrowingRecordApi {

    @Autowired
    private BorrowingRecordService borrowingRecordService;


    @GetMapping
    public ResponseEntity<List<BorrowingRecordDto>> getBorrowingRecordsWithFilter(
            @RequestParam Optional<String> patronName,
            @RequestParam Optional<String> bookTitle,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) {

        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(10));
        Page<BorrowingRecordDto> borrowingRecords = borrowingRecordService.findByPatronNameContainingOrBookTitleContaining(
                patronName.orElse(""),
                bookTitle.orElse(""),
                pageable);
        return ResponseEntity.ok(borrowingRecords.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowingRecordDto> getBorrowingRecordById(@PathVariable Long id) {
        Optional<BorrowingRecordDto> borrowingRecord = borrowingRecordService.findById(id);
        return borrowingRecord.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    //@Cacheable(value = "books", key = "", sync = true)
    public ResponseEntity<List<BorrowingRecordDto>> getAllBorrowingRecords() {
        List<BorrowingRecordDto> borrowingRecords = borrowingRecordService.findAll();
        return ResponseEntity.ok(borrowingRecords);
    }


    @PostMapping
    public ResponseEntity<BorrowingRecordDto> createBorrowingRecord(@Valid @RequestBody BorrowingRecordDto borrowingRecordDto) {
        BorrowingRecordDto savedBorrowingRecord = borrowingRecordService.save(borrowingRecordDto);
        return ResponseEntity.ok(savedBorrowingRecord);
    }


    @PutMapping("/{id}")
    public ResponseEntity<BorrowingRecordDto> updateBorrowingRecord(@PathVariable Long id, @Valid @RequestBody BorrowingRecordDto borrowingRecordDetails) {
        return borrowingRecordService.update(id, borrowingRecordDetails).map(borrowingRecord -> {
            return ResponseEntity.ok(borrowingRecord);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDto> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecordDto borrowingRecordDto = borrowingRecordService.borrowBook(bookId, patronId);
        return ResponseEntity.ok(borrowingRecordDto);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDto> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecordDto borrowingRecordDto = borrowingRecordService.returnBook(bookId, patronId);
        return ResponseEntity.ok(borrowingRecordDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBorrowingRecord(@PathVariable Long id) {
        return borrowingRecordService.deleteById(id)?
                ResponseEntity.ok().build():
                ResponseEntity.notFound().build();
    }
}
