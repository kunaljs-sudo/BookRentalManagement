package com.deom.BookManagementSystem.service.bookService;

import java.util.List;

import com.deom.BookManagementSystem.dto.BookDTO;
import com.deom.BookManagementSystem.entity.Book;
import com.deom.BookManagementSystem.exchanges.CreateBookRequest;
import com.deom.BookManagementSystem.exchanges.UpdateBookRequest;

public interface BookService {
    public List<BookDTO> getAllBooks();

    public BookDTO getBookById(Integer bookId);

    public Book getOnlyBookById(Integer bookId);

    public BookDTO rentBook(Integer bookId, String userEmail);

    public BookDTO returnBook(Integer bookId, String userEmail);

    public BookDTO createBook(CreateBookRequest createBookRequest);

    public BookDTO updateBook(Integer bookId, UpdateBookRequest updateBookRequest);

    public BookDTO updateBook(Book book);

    public BookDTO updateAvailabilityOfBook(Integer bookId, Boolean availabilityStatus);

    public String deleteBook(Integer bookId);
}
