package com.example.booky.readingList;

import com.example.booky.book.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reading_list_books")
public class ReadingListBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    private ReadingList list;

    @ManyToOne
    @JoinColumn(name = "book_isbn", nullable = false)
    private Book book;
}
