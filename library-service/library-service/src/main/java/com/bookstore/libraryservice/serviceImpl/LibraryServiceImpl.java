package com.bookstore.libraryservice.serviceImpl;

import com.bookstore.libraryservice.client.BookServiceClient;
import com.bookstore.libraryservice.dto.AddBookRequest;
import com.bookstore.libraryservice.dto.LibraryDto;
import com.bookstore.libraryservice.entity.Library;
import com.bookstore.libraryservice.repository.LibraryRepository;
import com.bookstore.libraryservice.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;
    private final BookServiceClient bookServiceClient;
    Logger logger = LoggerFactory.getLogger(LibraryRepository.class);
    @Override
    public LibraryDto getAllBooksInLibraryById(Long id) {
        logger.info("getAllBooksInLibraryById "+ id);

        Library library = libraryRepository.findById(id).orElse(null);

        LibraryDto libraryDto = new LibraryDto(library.getId(),
                library.getUserBooks()
                        .stream()
                        .map(book -> bookServiceClient.getBookById(book))
                        .collect(Collectors.toList()));
        return libraryDto;

    }

    @Override
    public LibraryDto createLibrary() {
        Library newLibrary = libraryRepository.save(new Library());

        LibraryDto libraryDto = LibraryDto.builder()
                .id(newLibrary.getId())
                .build();

        return libraryDto;

    }

    @Override
    public void addBookToLibrary(AddBookRequest addBookRequest) {
        Long bookId = bookServiceClient.getBookByIsbn(addBookRequest.getIsbn()).getBody().getId();
        Library library = libraryRepository.findById(addBookRequest.getLibraryId()).orElse(null);
        library.getUserBooks().add(bookId);
        libraryRepository.save(library);
    }
}
