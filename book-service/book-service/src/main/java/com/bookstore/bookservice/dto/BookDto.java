package com.bookstore.bookservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {
    Long id;
    String name;
    String author;
    int bookYear;
    String isbn;

}
