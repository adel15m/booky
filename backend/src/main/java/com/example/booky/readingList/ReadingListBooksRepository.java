package com.example.booky.readingList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingListBooksRepository extends JpaRepository<ReadingListBooks, Long> {
    Page<ReadingListBooks> findAllByList(ReadingList list, Pageable pageable);
}