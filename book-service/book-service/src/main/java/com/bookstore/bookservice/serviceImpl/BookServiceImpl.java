package com.bookstore.bookservice.serviceImpl;

import com.bookstore.bookservice.dto.BookCreateRequest;
import com.bookstore.bookservice.dto.BookDto;
import com.bookstore.bookservice.dto.BookResponse;
import com.bookstore.bookservice.entity.Book;
import com.bookstore.bookservice.exception.BookAlreadyExistsException;
import com.bookstore.bookservice.exception.BookNotFoundException;
import com.bookstore.bookservice.repository.BookRepository;
import com.bookstore.bookservice.service.BookService;
import com.bookstore.bookservice.service.ReadListService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private ReadListService readListService;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setReadListService(@Lazy ReadListService readListService) {
        this.readListService = readListService;
    }

    @Override
    public void createBook(BookCreateRequest bookCreateRequest) {
        Book findBook = bookRepository.findBookByIsbn(bookCreateRequest.isbn());

        if (findBook == null) {
            Book book = Book.builder()
                    .image(bookCreateRequest.image())
                    .name(bookCreateRequest.name())
                    .author(bookCreateRequest.author())
                    .bookYear(bookCreateRequest.bookYear())
                    .isbn(bookCreateRequest.isbn())
                    .fiction(bookCreateRequest.fiction())
                    .detail(bookCreateRequest.detail())
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
    public BookDto getBookByIsbn(String isbn) {
        return bookRepository.getBookByIsbn(isbn)
                .map(book -> new BookDto(book.getId(), book.getImage(), book.getName(), book.getAuthor()
                        , book.getBookYear(), book.getIsbn(), book.getFiction(), book.getDetail()))
                .orElseThrow(() -> new BookNotFoundException("Book could not found by isbn: " + isbn));
    }

    @Override
    @Transactional
    public void deleteBookByIsbn(String isbn) {
        bookRepository.getBookByIsbn(isbn).orElseThrow(() -> new BookNotFoundException("Book could not found by isbn: " + isbn));
        bookRepository.deleteBookByIsbn(isbn);
        readListService.deleteFromReadListByIsbn(isbn);
    }

    @Override
    public BookResponse updateBookByIsbn(BookCreateRequest bookCreateRequest) {
        Book findBook = bookRepository.getBookByIsbn(bookCreateRequest.isbn())
                .orElseThrow(() -> new BookNotFoundException("Book could not found by isbn: " + bookCreateRequest.isbn()));
        if (bookCreateRequest.image() != null)
            findBook.setImage(bookCreateRequest.image());
        findBook.setName(bookCreateRequest.name());
        findBook.setBookYear(bookCreateRequest.bookYear());
        findBook.setAuthor(bookCreateRequest.author());
        findBook.setFiction(bookCreateRequest.fiction());
        findBook.setDetail(bookCreateRequest.detail());
        bookRepository.save(findBook);

        BookResponse bookResponse = BookResponse.builder()
                .name(findBook.getName())
                .author(findBook.getAuthor())
                .bookYear(findBook.getBookYear())
                .isbn(findBook.getIsbn())
                .fiction(findBook.getFiction())
                .detail(findBook.getDetail())
                .build();

        return bookResponse;
    }

    @Override
    @Transactional
    public List<BookResponse> findBookByName(String text) {
        List<BookResponse> bookList = bookRepository.findBookByName(text).stream()
                .map(book -> convertBookToBookResponse(book))
                .collect(Collectors.toList());
        return bookList;
    }

    @Override
    public List<BookResponse> findBookByAuthor(String text) {
        List<BookResponse> bookList = bookRepository.findBooksByAuthor(text).stream()
                .map(book -> convertBookToBookResponse(book))
                .collect(Collectors.toList());
        return bookList;
    }

    public BookDto convertBookToBookDto(Book book) {
        BookDto bookDto = BookDto.builder()
                .id(book.getId())
                .image(book.getImage())
                .name(book.getName())
                .author(book.getAuthor())
                .bookYear(book.getBookYear())
                .isbn(book.getIsbn())
                .fiction(book.getFiction())
                .detail(book.getDetail())
                .build();
        return bookDto;
    }

    public BookResponse convertBookToBookResponse(Book book) {
        BookResponse bookResponse = BookResponse.builder()
                .id(book.getId())
                .image(book.getImage())
                .name(book.getName())
                .author(book.getAuthor())
                .bookYear(book.getBookYear())
                .isbn(book.getIsbn())
                .fiction(book.getFiction())
                .detail(book.getDetail())
                .build();
        return bookResponse;
    }
}
