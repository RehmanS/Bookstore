package com.bookstore.libraryservice.dto;

import lombok.Data;

@Data
public class AddBookRequest {
    Long libraryId;
    String isbn;
}
