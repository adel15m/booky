package com.example.booky.config;

import com.example.booky.common.exception.BookyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleBookyException(BookyException ex) {
        ErrorDto errorMessage = new ErrorDto(ex.getCode(),ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDto> handleAllExceptions(Throwable ex) {
        ErrorDto errorMessage = new ErrorDto("ERR-9000","something wrong happened");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}