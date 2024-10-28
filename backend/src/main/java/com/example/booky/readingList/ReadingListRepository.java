package com.example.booky.readingList;

import com.example.booky.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingListRepository extends JpaRepository<ReadingList, Long> {
    Page<ReadingList> findAllByUser(User user, Pageable pageable);
}
