package com.example.booky.book;

import com.example.booky.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBooksRepository extends JpaRepository<UserBooks, Long> {

    Page<UserBooks> findAllByUser(User user, Pageable pageable);


}
