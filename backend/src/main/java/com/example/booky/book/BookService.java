package com.example.booky.book;

import com.example.booky.book.dto.BookDto;
import com.example.booky.common.dto.PageableDto;
import com.example.booky.config.security.LoggedInUser;
import com.example.booky.openlibrary.OpenLibraryService;
import com.example.booky.openlibrary.dto.OLBookDto;
import com.example.booky.openlibrary.exception.OLBookNotFoundException;
import com.example.booky.user.User;
import com.example.booky.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookService {
    private final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserBooksRepository userBooksRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OpenLibraryService openLibraryService;

    public void addBook(String isbn) throws OLBookNotFoundException {
        String userName = LoggedInUser.getUserName();
        User user = userService.getUser(userName).orElseThrow();

        // Check if the user already has this book
        boolean alreadyExists = userBooksRepository.existsByUserAndBookIsbn(user, isbn);
        if (alreadyExists) {
            // If the user already has the book, skip adding it
            log.info("Book with ISBN {} already exists for user {} ", isbn, userName);
            return;
        }

        // Retrieve or save the book entity
        Book book;
        Optional<Book> bookOptional = bookRepository.findById(isbn);
        if (bookOptional.isPresent()) {
            book = bookOptional.get();
        } else {
            OLBookDto book1 = openLibraryService.getBook(isbn);
            book = Book.builder().isbn(isbn).title(book1.getTitle()).author(book1.getAuthor()).pages(book1.getPages()).build();
            book = bookRepository.save(book);
        }

        // Save the book-user relationship only if it doesn't already exist
        userBooksRepository.save(UserBooks.builder().book(book).user(user).build());
    }

    public PageableDto<BookDto> getBooks(Integer pageNumber, Integer pageSize) {
        String userName = LoggedInUser.getUserName();
        User user = userService.getUser(userName).orElseThrow();

        Sort sort = Sort.by("book.title").ascending();
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<UserBooks> r = userBooksRepository.findAllByUser(user, pageable);
        return PageableDto.<BookDto>builder()
                .totalElements(r.getTotalElements())
                .totalPages(r.getTotalPages())
                .elements(r.getContent().stream()
                        .map(UserBooks::getBook)
                        .map(book -> BookDto.builder()
                                .author(book.getAuthor())
                                .isbn(book.getIsbn())
                                .numberOfPages(book.getPages())
                                .title(book.getTitle())
                                .coverUrl("https://covers.openlibrary.org/b/isbn/" + book.getIsbn() + "-M.jpg")
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public Optional<Book> getBookForUser(String isbn, User user) {
        Optional<UserBooks> optionalUserBooks = userBooksRepository.findByUserAndBook_Isbn(user, isbn);
        return optionalUserBooks.map(UserBooks::getBook);
    }


}
