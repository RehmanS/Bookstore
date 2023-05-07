package com.bookstore.libraryservice.exception;

public class BookNotFoundByIdException extends RuntimeException{
    public BookNotFoundByIdException(String message) {
        super(message);
    }
}
