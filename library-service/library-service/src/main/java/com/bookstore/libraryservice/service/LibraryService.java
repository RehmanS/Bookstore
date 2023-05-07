package com.bookstore.libraryservice.service;

import com.bookstore.libraryservice.dto.AddBookRequest;
import com.bookstore.libraryservice.dto.CreateLibraryRequest;
import com.bookstore.libraryservice.dto.LibraryDto;

public interface LibraryService {

    LibraryDto getAllBooksInLibraryById(Long id);

    LibraryDto createLibrary(CreateLibraryRequest createLibraryRequest);

    void addBookToLibrary(AddBookRequest addBookRequest);

    void deleteLibraryById(Long id);

    void deleteBookFromLibrary(Long libId,Long bookId);


}
