package com.akeel.library.repository;

import com.akeel.library.model.BorrowingRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordJpaRepository extends JpaRepository<BorrowingRecord, Long> {

    @Query("SELECT br FROM BorrowingRecord br " +
            "JOIN br.patron p " +
            "JOIN br.book b " +
            "WHERE (:patronName IS NULL OR p.name LIKE %:patronName%) " +
            "AND (:bookTitle IS NULL OR b.title LIKE %:bookTitle%)")
    Page<BorrowingRecord> findByPatronNameContainingOrBookTitleContaining(String patronName, String bookTitle, Pageable pageable);
    Optional<BorrowingRecord> findByBookIdAndPatronId(Long bookId, Long patronId);
    Optional<BorrowingRecord> findByBookIdAndReturnDateIsNull(Long bookId);
}
