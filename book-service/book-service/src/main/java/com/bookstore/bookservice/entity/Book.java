package com.bookstore.bookservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Lob
    @Column(length = 100000)
    String image;

    String name;
    String author;
    int bookYear;
    String isbn;
    String fiction;

    @Lob
    @Column(columnDefinition = "text")
    String detail;

}
