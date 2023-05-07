package com.bookstore.libraryservice.exception;

public class BookAlreadyExistInLibraryException extends RuntimeException{
    public BookAlreadyExistInLibraryException(String message){
        super(message);
    }
}
