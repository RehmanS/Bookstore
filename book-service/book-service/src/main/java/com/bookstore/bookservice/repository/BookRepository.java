package com.bookstore.bookservice.repository;

import com.bookstore.bookservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> getBookByIsbn(String isbn);
    Book findBookByIsbn(String isbn);

    void deleteBookByIsbn(String isbn);

}

