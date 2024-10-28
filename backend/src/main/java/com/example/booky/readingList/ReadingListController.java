package com.example.booky.readingList;

import com.example.booky.openlibrary.exception.OLBookNotFoundException;
import com.example.booky.readingList.dto.ReadingListRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reading-list")
public class ReadingListController {
    @Autowired
    private ReadingListService readingListService;


    @PostMapping("/")
    public void createList(@RequestBody ReadingListRequestDto readingListRequestDto) throws OLBookNotFoundException {
        readingListService.createList(readingListRequestDto);
    }

}
