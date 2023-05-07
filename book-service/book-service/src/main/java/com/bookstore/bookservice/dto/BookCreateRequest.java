package com.bookstore.bookservice.dto;

import jakarta.validation.constraints.NotBlank;

public record BookCreateRequest(@NotBlank String name,
                                String author,
                                int bookYear,
                                @NotBlank String isbn) {
}
