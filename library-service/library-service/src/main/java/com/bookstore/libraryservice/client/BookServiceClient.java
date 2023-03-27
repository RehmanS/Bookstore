package com.bookstore.libraryservice.client;

import com.bookstore.libraryservice.dto.BookDto;
import com.bookstore.libraryservice.dto.BookIdDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "book-service", path = "/book")
public interface BookServiceClient {

    @GetMapping("{book-id}")
    BookDto getBookById(@PathVariable("book-id") Long id );

    @GetMapping("/isbn/{isbn}")
    ResponseEntity<BookIdDto> getBookByIsbn(@PathVariable("isbn") String isbn);

}
