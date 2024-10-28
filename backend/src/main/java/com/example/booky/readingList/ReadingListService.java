package com.example.booky.readingList;

import com.example.booky.config.security.LoggedInUser;
import com.example.booky.readingList.dto.ReadingListRequestDto;
import com.example.booky.user.User;
import com.example.booky.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadingListService {
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
}
