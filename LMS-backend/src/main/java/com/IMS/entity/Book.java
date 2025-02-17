package com.IMS.entity;

import jakarta.persistence.*;



@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "author",nullable = false)
    private String author;

    @Column(name = "isbn",nullable = false)
    private String isbn;

    @Column(name = "publicationYear",nullable = false)
    private Integer publicationYear;

    @Column(name = "description",nullable = false)
    private String description;

    public Book() {
    }

    public Book(String description, Integer publicationYear, String isbn, String author, String title, Long id) {
        this.description = description;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}