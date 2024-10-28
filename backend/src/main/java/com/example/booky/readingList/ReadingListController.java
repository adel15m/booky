package com.example.booky.readingList;

import com.example.booky.book.dto.BookDto;
import com.example.booky.book.dto.PageableDto;
import com.example.booky.openlibrary.exception.OLBookNotFoundException;
import com.example.booky.readingList.dto.ReadingListDto;
import com.example.booky.readingList.dto.ReadingListRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reading-list")
public class ReadingListController {
    @Autowired
    private ReadingListService readingListService;


    @PostMapping("/")
    public void createList(@RequestBody ReadingListRequestDto readingListRequestDto) throws OLBookNotFoundException {
        readingListService.createList(readingListRequestDto);
    }

    @GetMapping("/")
    public PageableDto<ReadingListDto> getReadingList(@RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
                                                      @RequestParam(value = "size", required = false, defaultValue = "6") Integer pageSize)  {
        return readingListService.getReadingList(pageNumber,pageSize);
    }

    @PostMapping("/{isbn}/{id}")
    public void addBookToList(@PathVariable("isbn") String isbn, @PathVariable("id") Long id){
        readingListService.addBookToList(isbn,id);
    }

    @GetMapping("/{id}/books")
    public PageableDto<BookDto> getBooksInList(@PathVariable("id") Long id,
                                               @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
                                               @RequestParam(value = "size", required = false, defaultValue = "6") Integer pageSize)  {
        return readingListService.getBooksInList(id,pageNumber,pageSize);
    }
}
