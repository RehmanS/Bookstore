package com.bookstore.libraryservice.exception;

import com.bookstore.libraryservice.exception.BookNotFoundByIdException;
import com.bookstore.libraryservice.exception.BookNotFoundException;
import com.bookstore.libraryservice.exception.ExceptionMessage;
import com.bookstore.libraryservice.exception.LibraryNotFoundException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GeneralExceptionHandler {

    //NOTE: Bu feign-den qayidan BookNotFoundExceptionu tutmaq ucundur
//    @ExceptionHandler(BookNotFoundException.class)
//    public ResponseEntity<ExceptionMessage> handle(BookNotFoundException exception) {
//        return new ResponseEntity<>(exception.getExceptionMessage(), HttpStatus.resolve(exception.getExceptionMessage().status()));
//    }

    @ExceptionHandler(LibraryNotFoundException.class)
    public ResponseEntity<?> handleLibraryNotFound(LibraryNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BookNotFoundByIdException.class)
    public ResponseEntity<?> handleBookNotFound(BookNotFoundByIdException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleWrongArgument(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Input parameter is not valid!");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Library id or isbn can't be blank!");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotAllowed(HttpRequestMethodNotSupportedException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Method Not Allowed: Make sure your request is correct");
    }

    @ExceptionHandler(BookAlreadyExistInLibraryException.class)
    public ResponseEntity<?> handleAlreadyExistInLibrary(BookAlreadyExistInLibraryException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
