package com.IMS.dto;

import jakarta.validation.constraints.NotBlank;

public class BookDto {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String isbn;

    @NotBlank
    private String publicationYear;

    @NotBlank
    private String description;

    public BookDto() {
    }

    public BookDto(String title, String author, String isbn, String publicationYear, String description) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.description = description;
    }

    public @NotBlank String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank String title) {
        this.title = title;
    }

    public @NotBlank String getAuthor() {
        return author;
    }

    public void setAuthor(@NotBlank String author) {
        this.author = author;
    }

    public @NotBlank String getIsbn() {
        return isbn;
    }

    public void setIsbn(@NotBlank String isbn) {
        this.isbn = isbn;
    }

    public @NotBlank String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(@NotBlank String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public @NotBlank String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank String description) {
        this.description = description;
    }
}
