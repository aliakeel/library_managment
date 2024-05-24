package com.akeel.library.service;

import com.akeel.library.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Page<BookDto> findByTitleContainingOrAuthorContaining(String title, String author, Pageable pageable);
    List<BookDto> findAll();
    List<BookDto> getAllAvailableBooks();
    Optional<BookDto> findById(Long id);
    BookDto save(BookDto book);
    Optional<BookDto> update(Long id, BookDto BookDto);
    boolean deleteById(Long id);
}
