package com.example.booky.controller;

import com.example.booky.Entity.Book;
import com.example.booky.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> addBookByIsbn(@RequestParam String isbn) {
        Book book = bookService.fetchAndSaveBookByIsbn(isbn);
        return ResponseEntity.ok(book);
    }
}
