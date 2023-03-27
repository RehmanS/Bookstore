package com.bookstore.bookservice.service;

import com.bookstore.bookservice.dto.BookDto;
import com.bookstore.bookservice.dto.BookIdDto;
import com.bookstore.bookservice.entity.Book;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto getBookByID(Long id);

    BookIdDto getBookByIsbn(String isbn);

}
