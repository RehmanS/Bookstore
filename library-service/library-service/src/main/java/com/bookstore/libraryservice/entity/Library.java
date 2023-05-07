package com.bookstore.libraryservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String libraryName;

    @ElementCollection
    List<Long> userBooks;
}
