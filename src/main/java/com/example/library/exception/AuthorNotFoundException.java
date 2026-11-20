package com.example.library.exception;

public class AuthorNotFoundException extends RuntimeException
{
    public AuthorNotFoundException(Long id)
    {
        super("author not found: " + id);
    }
}
