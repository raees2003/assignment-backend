package com.IMS.controller;

import com.IMS.dto.AIResponse;
import com.IMS.dto.BookDto;
import com.IMS.entity.Book;
import com.IMS.exception.BookNotFoundException;
import com.IMS.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> createBook(@Valid @RequestBody BookDto bookDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getValidationErrors(bindingResult));
        }
        Book createdBook = bookService.saveBook(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(bookService.getBookById(id));
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @Valid @RequestBody BookDto bookDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getValidationErrors(bindingResult));
        }
        return Optional.ofNullable(bookService.updateBook(id, bookDto))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookService.deleteBook(id));
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false, defaultValue = "") String title,
                                                  @RequestParam(required = false, defaultValue = "") String author) {
        return ResponseEntity.ok(bookService.searchBooks(title, author));
    }

    @GetMapping("/{id}/ai-insights")
    public ResponseEntity<AIResponse> getBookAiInsights(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookAiInsights(id));
    }

    private Map<String, String> getValidationErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }
}
