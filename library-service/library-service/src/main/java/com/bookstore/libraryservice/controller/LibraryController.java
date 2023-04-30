package com.bookstore.libraryservice.controller;

import com.bookstore.libraryservice.dto.AddBookRequest;
import com.bookstore.libraryservice.dto.LibraryDto;
import com.bookstore.libraryservice.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;
    @GetMapping("/{id}")
    public LibraryDto getLibraryById(@PathVariable("id") Long id) {
        return libraryService.getAllBooksInLibraryById(id);
    }

    @PostMapping
    public LibraryDto createLibrary(){
        return libraryService.createLibrary();
    }

    @PostMapping("/addBook")
    public void addBookToLibrary(@RequestBody AddBookRequest addBookRequest){
        libraryService.addBookToLibrary(addBookRequest);
    }

}
