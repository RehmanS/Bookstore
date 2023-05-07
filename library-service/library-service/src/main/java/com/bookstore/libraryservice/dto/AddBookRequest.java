package com.bookstore.libraryservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddBookRequest {
    @NotNull Long libraryId;
    @NotBlank String isbn;
}
