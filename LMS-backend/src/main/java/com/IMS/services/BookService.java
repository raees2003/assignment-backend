package com.IMS.services;

import com.IMS.dto.AIResponse;
import com.IMS.dto.BookDto;
import com.IMS.entity.Book;
import com.IMS.exception.BookNotFoundException;
import com.IMS.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.util.*;

@Service
public class BookService {

    private final RestTemplate restTemplate;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final String apiKey = System.getenv("Give the OpenAI API Key"); // give the key here

    public BookService(RestTemplate restTemplate, BookRepository bookRepository, ModelMapper modelMapper) {
        this.restTemplate = restTemplate;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public Book saveBook(BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
    }

    public Book updateBook(Long id, BookDto bookDto) {
        Book book = getBookById(id);
        modelMapper.map(bookDto, book);
        return bookRepository.save(book);
    }

    public String deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
        bookRepository.deleteById(id);
        return "Book is Deleted";
    }

    public List<Book> searchBooks(String title, String author) {
        return bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author);
    }

    public String generateAiSummary(String prompt) {
        try {
            String aiApiUrl = "https://api.openai.com/v1/chat/completions";

            HttpEntity<Map<String, Object>> request = prepareAiRequest(prompt);
            ResponseEntity<String> response = restTemplate.postForEntity(aiApiUrl, request, String.class);

            return response.getBody(); // Return actual AI response

        } catch (Exception e) {
            System.err.println("Error calling AI service: " + e.getMessage());
            return "AI service is currently unavailable.";
        }
    }

    private HttpEntity<Map<String, Object>> prepareAiRequest(String prompt) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4-turbo");
        requestBody.put("messages", List.of(Map.of("role", "user", "content", prompt)));
        requestBody.put("max_tokens", 50);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey); // Secure API key usage
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(requestBody, headers);
    }

    public AIResponse getBookAiInsights(Long id) {
        Book book = getBookById(id);
        String prompt = "Generate a tagline for a book titled '" + book.getTitle() +
                "' by " + book.getAuthor() + ": " + book.getDescription();
        String aiSummary = generateAiSummary(prompt);

        return new AIResponse(book.getTitle(), book.getAuthor(),
                book.getDescription(), aiSummary);
    }
}


