package com.akeel.library.controller;

import com.akeel.library.dto.BookDto;
import com.akeel.library.service.BookService;
import com.akeel.library.controller.api.BookApi;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
//import springfox.documentation.annotations.ApiIgnore;


import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
public class BookController implements BookApi {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getBooksWithFilter(
            @RequestParam Optional<String> title,
            @RequestParam Optional<String> author,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) {

        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(10));
        Page<BookDto> books = bookService.findByTitleContainingOrAuthorContaining(
                title.orElse(""),
                author.orElse(""),
                pageable);
        return ResponseEntity.ok(books.toList());
    }

    @GetMapping("/all")
    //@Cacheable(value = "books", key = "", sync = true)
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/available")
    public ResponseEntity<List<BookDto>> getAllAvailableBooks() {
        return ResponseEntity.ok(bookService.getAllAvailableBooks());
    }

    @GetMapping("/{id}")
    @Cacheable(value = "books", key = "#id", sync = true)
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        Optional<BookDto> book = bookService.findById(id);

        return book.map((r) -> {

                    return ResponseEntity.ok(book.get());
                })
                .orElseGet(() -> {
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    @CacheEvict(value = "books", allEntries = true)
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto savedBook = bookService.save(bookDto);
        return ResponseEntity.ok(savedBook);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "books", allEntries = true)
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto bookDetails) {
        return bookService.update(id,bookDetails)
                .map(
                        book -> ResponseEntity.ok(book)
                )
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "books", allEntries = true)
    public ResponseEntity<Object> deleteBook(@PathVariable Long id) {
        return (bookService.deleteById(id)) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }
}