package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.dto.AuthorRequest;
import com.example.library.exception.AuthorNotFoundException;
import com.example.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService
{
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository)
    {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll()
    {
        return authorRepository.findAll();
    } 

    public Author findById(Long id)
    {
        return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
    }

    public Author create(AuthorRequest authorRequest)
    {
        Author author = new Author();
        author.setFirstName(authorRequest.firstName());
        author.setLastName(authorRequest.lastName());
        author.setBirthYear(authorRequest.birthYear());
        author.setCountry(authorRequest.country());
        return authorRepository.save(author);
    }

    public Author update(Long id, AuthorRequest authorRequest)
    {
        Author existing = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
        existing.setBirthYear(authorRequest.birthYear());
        existing.setCountry(authorRequest.country());
        existing.setFirstName(authorRequest.firstName());
        existing.setLastName(authorRequest.lastName());
        return authorRepository.save(existing);
    } 

    public void delete(Long id)
    {
        if(!authorRepository.existsById(id))
        {
            throw new AuthorNotFoundException(id);
        }
        authorRepository.deleteById(id);
    }
}
