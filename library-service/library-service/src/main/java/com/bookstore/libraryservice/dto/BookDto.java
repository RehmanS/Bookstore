package com.bookstore.libraryservice.dto;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class BookDto {
    Long id;
    String name;
    int bookYear;
}
