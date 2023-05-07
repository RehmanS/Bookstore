package com.bookstore.bookservice.dto;

import lombok.Builder;

@Builder
public record BookResponse(String name,
                           String author,
                           int bookYear,
                           String isbn) {
}
