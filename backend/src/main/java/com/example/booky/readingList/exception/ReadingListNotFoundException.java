package com.example.booky.readingList.exception;

import com.example.booky.common.exception.BookyException;

public class ReadingListNotFoundException extends BookyException {

    public ReadingListNotFoundException() {
        super("ERR-002", "reading list does not exist");
    }
}