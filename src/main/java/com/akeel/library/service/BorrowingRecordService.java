package com.akeel.library.service;

import com.akeel.library.dto.BorrowingRecordDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BorrowingRecordService {
    Page<BorrowingRecordDto> findByPatronNameContainingOrBookTitleContaining(String patronName, String bookTitle, Pageable pageable);
    List<BorrowingRecordDto> findAll();
    Optional<BorrowingRecordDto> findById(Long id);
    BorrowingRecordDto save(BorrowingRecordDto borrowingRecordDto);
    Optional<BorrowingRecordDto> update(Long id, BorrowingRecordDto borrowingRecordDto);

    BorrowingRecordDto borrowBook(Long bookId, Long patronId);
    BorrowingRecordDto returnBook(Long bookId, Long patronId);
    boolean deleteById(Long id);
}
