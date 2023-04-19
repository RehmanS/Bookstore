package com.bookstore.libraryservice.controller;

import com.bookstore.libraryservice.dto.AddBookRequest;
import com.bookstore.libraryservice.dto.LibraryDto;
import com.bookstore.libraryservice.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class LibraryController {

    Logger logger = LoggerFactory.getLogger(LibraryController.class);
    private final LibraryService libraryService;

    @GetMapping("/{id}")
    public LibraryDto getLibraryById(@PathVariable("id") Long id) {
        logger.info("Get library by id: "+id);
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
