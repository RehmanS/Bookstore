package com.bookstore.bookservice.controller;

import com.bookstore.bookservice.dto.BookDto;
import com.bookstore.bookservice.dto.BookIdDto;
import com.bookstore.bookservice.service.BookService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAllBooks(){
       return bookService.getAllBooks();
    }

    @GetMapping("{book-id}")
    public BookDto getBookById(@PathVariable("book-id") Long id ){
        return bookService.getBookByID(id);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookIdDto> getBookByIsbn(@PathVariable @NotEmpty String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

}
