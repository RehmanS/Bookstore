package com.bookstore.libraryservice.serviceImpl;

import com.bookstore.libraryservice.client.BookServiceClient;
import com.bookstore.libraryservice.dto.AddBookRequest;
import com.bookstore.libraryservice.dto.CreateLibraryRequest;
import com.bookstore.libraryservice.dto.LibraryDto;
import com.bookstore.libraryservice.dto.LibraryResponse;
import com.bookstore.libraryservice.entity.Library;
import com.bookstore.libraryservice.exception.BookAlreadyExistInLibraryException;
import com.bookstore.libraryservice.exception.BookNotFoundByIdException;
import com.bookstore.libraryservice.exception.LibraryNotFoundException;
import com.bookstore.libraryservice.repository.LibraryRepository;
import com.bookstore.libraryservice.service.LibraryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final LibraryRepository libraryRepository;
    private final BookServiceClient bookServiceClient;


    @Override
    public List<LibraryResponse> getAllLibraries() {
        return libraryRepository.findAll().stream().map(this::convertLibraryToLibraryResponse).collect(Collectors.toList());
    }

    @Override
    public LibraryDto getAllBooksInLibraryById(Long id) {
        Library library = libraryRepository.findById(id).orElseThrow(() -> new LibraryNotFoundException("Library could not found by id: " + id));

        LibraryDto libraryDto = LibraryDto.builder()
                .libraryName(library.getLibraryName())
                .id(library.getId())
                .userBookList(library.getUserBooks()
                        .stream()
                        .map(book -> bookServiceClient.getBookById(book))
                        .collect(Collectors.toList()))
                .build();
        return libraryDto;
    }

    @Override
    public LibraryDto createLibrary(CreateLibraryRequest createLibraryRequest) {
        Library newLibrary = new Library();
        newLibrary.setLibraryName(createLibraryRequest.libraryName());
        libraryRepository.save(newLibrary);

        LibraryDto libraryDto = LibraryDto.builder()
                .id(newLibrary.getId())
                .libraryName(createLibraryRequest.libraryName())
                .build();

        return libraryDto;
    }

    @Override
    @Transactional
    public void addBookToLibrary(AddBookRequest addBookRequest) {
        Long bookId = bookServiceClient.getBookByIsbn(addBookRequest.getIsbn()).getBody().getId();
        Library library = libraryRepository.findById(addBookRequest.getLibraryId()).
                orElseThrow(() -> new LibraryNotFoundException("Library could not found by id: " + addBookRequest.getLibraryId()));
        if (library.getUserBooks().contains(bookId) & bookId!=-1) {
            throw new BookAlreadyExistInLibraryException("Book already exist in Library. isbn: "+addBookRequest.getIsbn());
        } else {
            library.getUserBooks().add(bookId);
            libraryRepository.save(library);
        }
    }

    @Override
    public void deleteLibraryById(Long id) {
        Library library = libraryRepository.findById(id).orElseThrow(() -> new LibraryNotFoundException("Library could not found by id: " + id));
        libraryRepository.deleteById(id);
    }

    @Override
    public void deleteBookFromLibrary(Long libId, Long bookId) {
        Library library = libraryRepository.findById(libId).orElseThrow(() -> new LibraryNotFoundException("Library could not found by id: " + libId));
        List<Long> userBooks = library.getUserBooks();
        if (userBooks.contains(bookId)) {
            userBooks.remove(bookId);
            libraryRepository.save(library);
        } else {
            throw new BookNotFoundByIdException("Book could not found by id: " + bookId);
        }
    }

    public LibraryResponse convertLibraryToLibraryResponse(Library library){
        return LibraryResponse.builder()
                .id(library.getId())
                .libraryName(library.getLibraryName())
                .build();
    }
}
