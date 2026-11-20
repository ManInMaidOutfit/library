package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.dto.BookRequest;
import com.example.library.repository.BookRepository;
import com.example.library.exception.BookNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class BookService
{
   private final BookRepository bookRepository;
   private final AuthorService authorService;
   public BookService(BookRepository bookRepository, AuthorService authorService)
   {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
   }

   public List<Book> findAll()
   {
        return bookRepository.findAll();
   }

   public Book findById(Long id)
   {
        return bookRepository.findById(id)
        .orElseThrow(() -> new BookNotFoundException(id));
   }

   public Book create(BookRequest bookRequest)
   {
        Book book = new Book();
        book.setGenre(bookRequest.genre());
        book.setIsbn(bookRequest.isbn());
        book.setTitle(bookRequest.title());
        book.setPublicationYear(bookRequest.publicationYear());
        book.setAuthor(authorService.findById(bookRequest.authorId()));
        return bookRepository.save(book);
   }

   public Book update(Long id, BookRequest bookRequest)
   {
        Book existing = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        existing.setGenre(bookRequest.genre());
        existing.setIsbn(bookRequest.isbn());
        existing.setTitle(bookRequest.title());
        existing.setPublicationYear(bookRequest.publicationYear());
        existing.setAuthor(authorService.findById(bookRequest.authorId()));
        return bookRepository.save(existing);
   }

   public void delete(Long id)
   {
        if(!bookRepository.existsById(id))
        {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
   }
}
