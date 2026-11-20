package com.example.library.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.entity.Author;
import com.example.library.service.AuthorService;
import com.example.library.dto.AuthorRequest;
import com.example.library.dto.AuthorResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;

import jakarta.validation.Valid;


@RestController 
@RequestMapping("/authors")
public class AuthorController
{
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService)
    {
        this.authorService = authorService;
    }

    @GetMapping  
    public List<AuthorResponse> getAll()
    {
        return authorService.findAll().stream().map(AuthorResponse::from).toList();
    }

    @GetMapping("/{id}")
    public AuthorResponse getAuthorById(@PathVariable Long id)
    {
        return AuthorResponse.from(authorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> create(@Valid @RequestBody AuthorRequest authorRequest)
    {
        Author created = authorService.create(authorRequest);
        AuthorResponse response = AuthorResponse.from(created);
        return ResponseEntity.created(URI.create("/authors/" + created.getId())).body(response);
    }
    
    @PutMapping("/{id}")
    public AuthorResponse update(@PathVariable Long id, @Valid @RequestBody AuthorRequest authorRequest)
    {
        Author updated = authorService.update(id, authorRequest);
        return AuthorResponse.from(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
