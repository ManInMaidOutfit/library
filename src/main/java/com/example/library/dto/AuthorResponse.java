package com.example.library.dto;

import com.example.library.entity.Author;

public record AuthorResponse(
    Long id,
    String firstName,
    String lastName,
    Integer birthYear,
    String country
)
{
    public static AuthorResponse from(Author author)
    {
        return new AuthorResponse(
            author.getId(), 
            author.getFirstName(), 
            author.getLastName(), 
            author.getBirthYear(), 
            author.getCountry());
    }
}
