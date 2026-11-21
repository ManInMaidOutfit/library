package com.example.library.controller;

import com.example.library.service.BookService;
import com.example.library.dto.BookRequest;
import com.example.library.dto.BookResponse;
import com.example.library.entity.Book;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

@RestController 
@RequestMapping("/books")
public class BookController
{
    private final BookService bookservice;
    public BookController(BookService bookservice)
    {
        this.bookservice = bookservice;
    }

    @GetMapping
    public List<BookResponse> getAll()
    {
        return bookservice.findAll().stream().map(BookResponse::from).toList();
    }

    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable Long id)
    {
        return BookResponse.from(bookservice.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest bookRequest)
    {
        Book book = bookservice.create(bookRequest);
        BookResponse response = BookResponse.from(book);
        return ResponseEntity.created(URI.create("/books/" + book.getId())).body(response);
    }

    @PutMapping("/{id}")
    public BookResponse updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest bookRequest)
    {
        Book updated = bookservice.update(id, bookRequest);
        return BookResponse.from(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        bookservice.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("BookController работает!");
    }
}
