package com.example.booky.readingList;

import com.example.booky.book.Book;
import com.example.booky.book.BookService;
import com.example.booky.book.dto.BookDto;
import com.example.booky.common.dto.PageableDto;
import com.example.booky.config.security.LoggedInUser;
import com.example.booky.readingList.dto.ReadingListDto;
import com.example.booky.readingList.dto.ReadingListRequestDto;
import com.example.booky.readingList.exception.ReadingListNotFoundException;
import com.example.booky.user.User;
import com.example.booky.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReadingListService {
    @Autowired
    private BookService bookService;
    @Autowired
    private ReadingListBooksRepository readingListBooksRepository;
    @Autowired
    private ReadingListRepository readingListRepository;
    @Autowired
    private UserService userService;

    public void createList(ReadingListRequestDto readingListRequestDto) {
        String userName = LoggedInUser.getUserName();
        User user = userService.getUser(userName).orElseThrow();
        readingListRepository.save(ReadingList.builder()
                .name(readingListRequestDto.getName())
                .user(user)
                .build());
    }

    public PageableDto<ReadingListDto> getReadingList(Integer pageNumber, Integer pageSize) {

        String userName = LoggedInUser.getUserName();
        User user = userService.getUser(userName).orElseThrow();

        Sort sort = Sort.by("name").ascending();
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<ReadingList> r = readingListRepository.findAllByUser(user, pageable);

        return PageableDto.<ReadingListDto>builder()
                .totalElements(r.getTotalElements())
                .totalPages(r.getTotalPages())
                .elements(r.getContent().stream()
                        .map(readingList -> ReadingListDto.builder()
                                .id(readingList.getId())
                                .name(readingList.getName())
                                .build())
                        .collect(Collectors.toList()))
                .build();

    }

    public void addBookToList(String isbn, Long id) {
        String userName = LoggedInUser.getUserName();
        User user = userService.getUser(userName).orElseThrow();

        ReadingList readingList = readingListRepository.findById(id).orElseThrow();
        if (!readingList.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("you dont own this list");
        }
        Optional<Book> optionalBook = bookService.getBookForUser(isbn, user);

        if (optionalBook.isEmpty()) {
            throw new RuntimeException("you dont have book in library");
        }
        Book book = optionalBook.get();

        readingListBooksRepository.save(ReadingListBooks
                .builder()
                .list(readingList)
                .book(book)
                .build());
    }

    public PageableDto<BookDto> getBooksInList(Long id, Integer pageNumber, Integer pageSize) throws ReadingListNotFoundException {
        String userName = LoggedInUser.getUserName();
        User user = userService.getUser(userName).orElseThrow();

        ReadingList readingList = readingListRepository.findById(id).orElseThrow(ReadingListNotFoundException::new);
        if (!readingList.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("you dont own this list");
        }

        Sort sort = Sort.by("book.title").ascending();
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<ReadingListBooks> r = readingListBooksRepository.findAllByList(readingList, pageable);
        return PageableDto.<BookDto>builder()
                .totalElements(r.getTotalElements())
                .totalPages(r.getTotalPages())
                .elements(r.getContent().stream()
                        .map(ReadingListBooks::getBook)
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
}
