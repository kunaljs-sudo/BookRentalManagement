package com.deom.BookManagementSystem.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deom.BookManagementSystem.dto.BookDTO;
import com.deom.BookManagementSystem.exception.BadDataProvidedException;
import com.deom.BookManagementSystem.exchanges.CreateBookRequest;
import com.deom.BookManagementSystem.exchanges.UpdateBookRequest;
import com.deom.BookManagementSystem.service.bookService.BookService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/books")
@Slf4j
public class BookController {

    @Autowired
    private BookService bookService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/allBook")
    public List<BookDTO> getBooks() {
        log.info("Get All Books Get Request");
        return bookService.getAllBooks();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/createBook")
    public BookDTO createBook(@RequestBody CreateBookRequest createBookRequest) {
        if (createBookRequest == null) {
            log.error("Bad data proivded for CreateBookRequest");
            throw new BadDataProvidedException("Bad data proivded for CreateBookRequest");
        }
        log.info("Create Book Reuqest : Only By ADMIN");
        return bookService.createBook(createBookRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/deleteBook/{bookId}")
    public String deleteBook(@PathVariable Integer bookId) {
        log.info("Delete Book Reuqest : Only By ADMIN");
        return bookService.deleteBook(bookId);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PutMapping("/{bookId}/rent")
    public BookDTO rentBook(@PathVariable Integer bookId, Principal principal) {
        log.info("Rent Book Reuqest: ");
        return bookService.rentBook(bookId, principal.getName());
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PutMapping("/{bookId}/return")
    public BookDTO returnBook(@PathVariable Integer bookId, Principal principal) {
        log.info("Return Book Reuqest: ");
        return bookService.returnBook(bookId, principal.getName());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{bookId}/update")
    public BookDTO updateBook(@PathVariable Integer bookId, @RequestBody UpdateBookRequest updateBookRequest) {
        log.info("Update Book Reuqest: Only By ADMIN");
        return bookService.updateBook(bookId, updateBookRequest);
    }
}
