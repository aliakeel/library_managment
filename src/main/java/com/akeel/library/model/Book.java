package com.akeel.library.model;


import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private int publicationYear;

    @Column(nullable = false, unique = true)
    private String isbn;

    public Book setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Book setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
        return this;
    }

    public Book setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }
}