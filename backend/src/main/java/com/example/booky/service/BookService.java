package com.example.booky.service;

import com.example.booky.Entity.Book;
import com.example.booky.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book fetchAndSaveBookByIsbn(String isbn) {
        // Call Open Library API to fetch details and save book
        return null;
    }


}
