package com.example.booky.book.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {
    private String isbn;
    private String title;
    private String author;
    private Integer numberOfPages;
    private String coverUrl;
}
