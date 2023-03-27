package com.bookstore.libraryservice.controller;

import com.bookstore.libraryservice.exception.BookNotFoundException;
import com.bookstore.libraryservice.exception.ExceptionMessage;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handle(BookNotFoundException exception) {
        return new ResponseEntity<>(exception.getExceptionMessage(), HttpStatus.resolve(exception.getExceptionMessage().status()));
    }

}
