package com.akeel.library.service.impl;

import com.akeel.library.mapping.Mapping;
import com.akeel.library.model.Book;
import com.akeel.library.model.BorrowingRecord;
import com.akeel.library.model.Patron;
import com.akeel.library.repository.BookJpaRepository;
import com.akeel.library.repository.BorrowingRecordJpaRepository;
import com.akeel.library.repository.PatronJpaRepository;
import com.akeel.library.service.BorrowingRecordService;
import com.akeel.library.dto.BorrowingRecordDto;
import com.akeel.library.exception.BookAlreadyBorrowedException;
import com.akeel.library.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    @Autowired
    private BorrowingRecordJpaRepository borrowingRecordRepository;

    @Autowired
    private BookJpaRepository bookRepository;

    @Autowired
    private PatronJpaRepository patronRepository;
    @Override
    public Page<BorrowingRecordDto> findByPatronNameContainingOrBookTitleContaining(String title, String author, Pageable pageable) {
        List<BorrowingRecordDto> borrowingRecords = borrowingRecordRepository.findByPatronNameContainingOrBookTitleContaining(title,author,pageable).stream()
                .map(borrowingRecord -> Mapping.toBorrowingRecordDto(borrowingRecord))
                .toList();
        return new PageImpl<>(borrowingRecords, pageable, borrowingRecords.size());
    }

    @Override
    public List<BorrowingRecordDto> findAll() {
        return borrowingRecordRepository.findAll().stream().map((borrowingRecord -> Mapping.toBorrowingRecordDto(borrowingRecord))).toList();
    }

    @Override
    public Optional<BorrowingRecordDto> findById(Long id) {
        return Optional.of(Mapping.toBorrowingRecordDto(borrowingRecordRepository.findById(id).get()));
    }

    @Transactional
    public BorrowingRecordDto save(BorrowingRecordDto borrowingRecord) {
        return Mapping.toBorrowingRecordDto(borrowingRecordRepository.save(Mapping.toBorrowingRecord(borrowingRecord)));
    }


    @Transactional
    public Optional<BorrowingRecordDto> update(Long id, BorrowingRecordDto borrowingRecordDto) {
        Optional<BorrowingRecord> existingBorrowingRecord = borrowingRecordRepository.findById(id);
        if (existingBorrowingRecord.isPresent()) {
            BorrowingRecord borrowingRecord = Mapping.toBorrowingRecord(borrowingRecordDto);
            return Optional.of(Mapping.toBorrowingRecordDto(borrowingRecordRepository.save(borrowingRecord)));
        } else {
            // Handle not found
            return null;
        }
    }


    @Override
    public BorrowingRecordDto borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + patronId));

        // Check if the book is already borrowed
        Optional<BorrowingRecord> existingBorrowingRecord = borrowingRecordRepository.findByBookIdAndReturnDateIsNull(bookId);
        if (existingBorrowingRecord.isPresent()) {
            throw new BookAlreadyBorrowedException("Book with id: " + bookId + " is already borrowed.");
        }

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());
        borrowingRecord.setReturnDate(null);

        return Mapping.toBorrowingRecordDto(borrowingRecordRepository.save(borrowingRecord));
    }

    @Override
    public BorrowingRecordDto returnBook(Long bookId, Long patronId) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found for book id: " + bookId + " and patron id: " + patronId));

        borrowingRecord.setReturnDate(LocalDate.now());
        return Mapping.toBorrowingRecordDto(borrowingRecordRepository.save(borrowingRecord));
    }


    @Transactional
    public boolean deleteById(Long id) {
        Optional<BorrowingRecord> borrowingRecord = borrowingRecordRepository.findById(id);
        try {
            borrowingRecordRepository.delete(borrowingRecord.get());
            return true;
        }catch (Exception e){

        }
        return false;
    }

}

