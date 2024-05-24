package com.akeel.library.service.impl;

import com.akeel.library.dto.BookDto;
import com.akeel.library.mapping.Mapping;
import com.akeel.library.model.Book;
import com.akeel.library.repository.BookJpaRepository;
import com.akeel.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookJpaRepository bookRepository;

    @Override
    public Page<BookDto> findByTitleContainingOrAuthorContaining(String title, String author, Pageable pageable) {
        List<BookDto> books = bookRepository.findByTitleContainingOrAuthorContaining(title,author,pageable).stream()
                .map(book -> Mapping.toBookDto(book))
                .toList();
        return new PageImpl<>(books, pageable, books.size());
    }

    @Override
    public List<BookDto> getAllAvailableBooks() {
        return bookRepository.findAllAvailableBooks().stream().map(book -> Mapping.toBookDto(book)).toList();
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map((book -> Mapping.toBookDto(book))).toList();
    }

    @Override
    public Optional<BookDto> findById(Long id) {
        return Optional.of(Mapping.toBookDto(bookRepository.findById(id).get()));
    }

    @Transactional
    public BookDto save(BookDto book) {
        return Mapping.toBookDto(bookRepository.save(Mapping.toBook(book)));
    }


    @Transactional
    public Optional<BookDto> update(Long id, BookDto bookDto) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            Book book = Mapping.toBook(bookDto);
            return Optional.of(Mapping.toBookDto(bookRepository.save(book)));
        } else {
            // Handle not found
            return null;
        }
    }

    @Transactional
    public boolean deleteById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        try {
            bookRepository.delete(book.get());
            return true;
        }catch (Exception e){

        }
        return false;
    }

}

