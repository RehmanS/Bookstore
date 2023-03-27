package com.bookstore.libraryservice.service;

import com.bookstore.libraryservice.dto.AddBookRequest;
import com.bookstore.libraryservice.dto.LibraryDto;
import com.bookstore.libraryservice.entity.Library;

public interface LibraryService {

    LibraryDto getAllBooksInLibraryById(Long id);

    LibraryDto createLibrary();

    void addBookToLibrary(AddBookRequest addBookRequest);

}
