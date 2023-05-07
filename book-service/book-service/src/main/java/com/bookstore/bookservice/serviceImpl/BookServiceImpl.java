package com.bookstore.bookservice.serviceImpl;
import com.bookstore.bookservice.dto.BookCreateRequest;
import com.bookstore.bookservice.dto.BookDto;
import com.bookstore.bookservice.dto.BookIdDto;
import com.bookstore.bookservice.dto.BookResponse;
import com.bookstore.bookservice.entity.Book;
import com.bookstore.bookservice.exception.BookAlreadyExistsException;
import com.bookstore.bookservice.exception.BookNotFoundException;
import com.bookstore.bookservice.repository.BookRepository;
import com.bookstore.bookservice.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    @Override
    public void createBook(BookCreateRequest bookCreateRequest) {
        Book findBook = bookRepository.findBookByIsbn(bookCreateRequest.isbn());

        if (findBook == null) {
            Book book = Book.builder()
                    .name(bookCreateRequest.name())
                    .author(bookCreateRequest.author())
                    .bookYear(bookCreateRequest.bookYear())
                    .isbn(bookCreateRequest.isbn())
                    .build();
            bookRepository.save(book);
        } else {
            throw new BookAlreadyExistsException("The book already exists. isbn: " + bookCreateRequest.isbn());
        }
    }

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
                .orElseThrow(() -> new BookNotFoundException("Book could not found by id: " + id));
    }

    @Override
    public BookIdDto getBookByIsbn(String isbn) {
        return bookRepository.getBookByIsbn(isbn)
                .map(book -> new BookIdDto(book.getId(), book.getIsbn()))
                .orElseThrow(() -> new BookNotFoundException("Book could not found by isbn: " + isbn));
    }

    @Override
    @Transactional
    public void deleteBookByIsbn(String isbn) {
        bookRepository.getBookByIsbn(isbn).orElseThrow(() -> new BookNotFoundException("Book could not found by isbn: " + isbn));
        bookRepository.deleteBookByIsbn(isbn);
    }

    @Override
    public BookResponse updateBookByIsbn(BookCreateRequest bookCreateRequest) {
        Book findBook = bookRepository.getBookByIsbn(bookCreateRequest.isbn())
                .orElseThrow(() -> new BookNotFoundException("Book could not found by isbn: " + bookCreateRequest.isbn()));
        findBook.setName(bookCreateRequest.name());
        findBook.setBookYear(bookCreateRequest.bookYear());
        findBook.setAuthor(bookCreateRequest.author());
        bookRepository.save(findBook);

        BookResponse bookResponse = BookResponse.builder()
                .name(findBook.getName())
                .author(findBook.getAuthor())
                .bookYear(findBook.getBookYear())
                .isbn(findBook.getIsbn())
                .build();

        return bookResponse;
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
