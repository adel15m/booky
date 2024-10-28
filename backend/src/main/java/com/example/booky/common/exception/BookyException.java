package com.example.booky.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookyException extends Exception {
    private String code;
    private String message;
}
