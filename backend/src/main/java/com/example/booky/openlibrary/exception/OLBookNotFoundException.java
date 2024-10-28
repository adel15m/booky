package com.example.booky.openlibrary.exception;

import com.example.booky.common.exception.BookyException;

public class OLBookNotFoundException extends BookyException {

    public OLBookNotFoundException() {
        super("ERR-001","the isbn was not found in Open Library");
    }
}
