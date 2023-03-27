package com.bookstore.bookservice.serviceImpl;

import com.bookstore.bookservice.dto.BookDto;
import com.bookstore.bookservice.dto.BookIdDto;
import com.bookstore.bookservice.entity.Book;
import com.bookstore.bookservice.exception.BookNotFoundException;
import com.bookstore.bookservice.repository.BookRepository;
import com.bookstore.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> convertBookToBookDto(book))
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBookByID(Long id) {
        return bookRepository.findById(id)
                .map(book -> convertBookToBookDto(book))
                .orElseThrow(()->new BookNotFoundException("Book could not found by id:" + id));
    }

    @Override
    public BookIdDto getBookByIsbn(String isbn) {
        return bookRepository.getBookByIsbn(isbn)
                .map(book -> new BookIdDto(book.getId(), book.getIsbn()))
                .orElseThrow(() -> new BookNotFoundException("Book could not found by isbn: " + isbn));
    }

    public BookDto convertBookToBookDto(Book book) {
        BookDto bookDto = BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .bookYear(book.getBookYear())
                .isbn(book.getIsbn())
                .build();
        return bookDto;
    }
}
