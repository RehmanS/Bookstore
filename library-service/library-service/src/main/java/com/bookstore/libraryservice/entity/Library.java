package com.bookstore.libraryservice.entity;


import com.bookstore.libraryservice.dto.BookDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ElementCollection
    List<Long> userBooks;
}
