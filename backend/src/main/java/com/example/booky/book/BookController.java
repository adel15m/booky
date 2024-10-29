package com.example.booky.book;

import com.example.booky.book.dto.BookDto;
import com.example.booky.common.dto.PageableDto;
import com.example.booky.openlibrary.exception.OLBookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/")
    public void addBookByIsbn(@RequestParam String isbn) throws OLBookNotFoundException {
        bookService.addBook(isbn);
    }

    @GetMapping("/")
    public PageableDto<BookDto> getBooks(@RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
                                         @RequestParam(value = "size", required = false, defaultValue = "6") Integer pageSize) {
        return bookService.getBooks(pageNumber, pageSize);
    }
}

