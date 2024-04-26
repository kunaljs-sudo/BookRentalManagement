package com.deom.BookManagementSystem.service.bookService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deom.BookManagementSystem.dto.BookDTO;
import com.deom.BookManagementSystem.entity.Book;
import com.deom.BookManagementSystem.entity.User;
import com.deom.BookManagementSystem.exception.BadDataProvidedException;
import com.deom.BookManagementSystem.exception.BookNotFoundException;
import com.deom.BookManagementSystem.exception.UnableToRentBookException;
import com.deom.BookManagementSystem.exception.UnableToReturnBookException;
import com.deom.BookManagementSystem.exception.UserNameNoFoundException;
import com.deom.BookManagementSystem.exchanges.CreateBookRequest;
import com.deom.BookManagementSystem.exchanges.UpdateBookRequest;
import com.deom.BookManagementSystem.mapper.BookMapper;
import com.deom.BookManagementSystem.repository.BookRepository;
import com.deom.BookManagementSystem.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    private final Integer allowedBooksToRent = 2;

    @Override
    public List<BookDTO> getAllBooks() {
        log.info("Fetching All Books from BookRepository and convert to BookDTO");
        return bookRepository.findAll().stream().map(book -> BookMapper.mapBook2DTO(book)).collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Integer bookId) {
        log.info("Fetching Book from BookRepository with bookId: " + bookId);
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            log.error("Book not present for provided book id: " + bookId);
            throw new BookNotFoundException("Book not present for provided book id: " + bookId);
        }
        log.info("Successfully retrived book from BookRepository bookId: " + bookId);
        return BookMapper.mapBook2DTO(optionalBook.get());
    }

    @Override
    public Book getOnlyBookById(Integer bookId) {
        log.info("Fetching Book from BookRepository with bookId: " + bookId);
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            log.error("Book not present for provided book id: " + bookId);
            throw new BookNotFoundException("Book not present for provided book id: " + bookId);
        }
        log.info("Successfully retrived book from BookRepository bookId: " + bookId);
        return optionalBook.get();
    }

    @Override
    public BookDTO createBook(CreateBookRequest createBookRequest) {

        Book book = new Book();
        book.setAuthor(createBookRequest.getAuthor());
        book.setGenre(createBookRequest.getGenre());
        book.setTitle(createBookRequest.getTitle());
        book.setAvailabilityStatus(true);

        log.info("Trying to create book by given CreateBookRequest");
        try {

            Book createdBook = bookRepository.save(book);
            return BookMapper.mapBook2DTO(createdBook);
        } catch (Exception e) {
            log.error("Something went wrong with CreateBookRequest object", e);
            throw new BadDataProvidedException("Something went wrong with CreateBookRequest object");
        }
    }

    @Override
    public BookDTO rentBook(Integer bookId, String email) {
        log.info("Fecthing user from UserRepository");
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            log.error("User email not found: " + email, UserNameNoFoundException.class);
            throw new UserNameNoFoundException("User email not found: " + email);
        }
        log.info("User data retrieved");
        User user = optionalUser.get();

        log.info("renting Book with bookId: " + bookId);
        if (user.getBooks().size() == allowedBooksToRent) {
            log.error("User Already have maximum allowed books: " + allowedBooksToRent + ", can't rent more",
                    UnableToRentBookException.class);
            throw new UnableToRentBookException("You already have maximum allowed books, can't rent more");
        }
        log.info("Fecthing Book for given BookId: " + bookId);
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            log.error("Book not present for provided book id: " + bookId);
            throw new BookNotFoundException("Book not present for provided book id: " + bookId);
        }

        log.info("Retrived Book from BookRepository");
        Book book = optionalBook.get();

        // if book is not avaialable to rent
        log.info("Check if book is not avaialable to rent");
        if (book.isAvailabilityStatus() == false || book.getRentedBy() != null) {
            log.error("Book you are trying to rent is not available", UnableToRentBookException.class);
            throw new UnableToRentBookException("Book you are trying to rent is not available");
        }
        // set availability status as false if available
        log.info("Book Available to rent");
        log.info("Change book availablity to false and set RentedBy in Book Obect");
        book.setAvailabilityStatus(false);
        book.setRentedBy(user);

        try {
            BookDTO bookDTO = BookMapper.mapBook2DTO(bookRepository.save(book));
            log.info("Successfully Rented Book");
            return bookDTO;
        } catch (Exception e) {
            log.error("Something went wrong whlie saving book object", e);
            throw new UnableToRentBookException("Unable to rent book something went wrong");
        }

    }

    public BookDTO returnBook(Integer bookId, String email) {

        log.info("Fetchinf Book from BookRepository");
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isEmpty()) {
            log.error("Book not present for provided book id:" + bookId, BookNotFoundException.class);
            throw new BookNotFoundException("Book not present for provided book id: " + bookId);
        }
        Book book = optionalBook.get();
        // if book is available or book rentedby null or whoever rented book is not same
        // user then throw exception
        log.info(
                "Check if book is available or book rentedby null or whoever rented book is not same user loggedIn then throw exception");
        if (book.isAvailabilityStatus() == true || book.getRentedBy() == null
                || !email.equals(book.getRentedBy().getEmail())) {
            log.error("Attempt to return book failed it is not being rented by userEmail: " + email);
            throw new UnableToReturnBookException("Attempt to return book failed it is not being rented by you");
        }

        book.setAvailabilityStatus(true);
        book.setRentedBy(null);
        try {
            BookDTO bookDTO = BookMapper.mapBook2DTO(bookRepository.save(book));
            log.info("Successfully Returned Book");
            return bookDTO;
        } catch (Exception e) {
            log.error("Something went wrong while Returning book", e);
            throw new UnableToRentBookException("Something went wrong while Returning book" + e.getMessage());
        }
    }

    @Override
    public BookDTO updateBook(Integer bookId, UpdateBookRequest updateBookRequest) {
        log.info("Fetching Book from BookRepository");
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            log.error("Book not present for provided book id: " + bookId, BookNotFoundException.class);
            throw new BookNotFoundException("Book not present for provided book id: " + bookId);
        }
        log.info("Succesfuuly retrived book and updating the changes, bookID " + bookId);
        Book book = optionalBook.get();
        book.setAuthor(updateBookRequest.getAuthor() != null ? updateBookRequest.getAuthor() : book.getAuthor());
        book.setGenre(updateBookRequest.getGenre() != null ? updateBookRequest.getGenre() : book.getGenre());
        book.setTitle(updateBookRequest.getTitle() != null ? updateBookRequest.getTitle() : book.getTitle());
        // get the udated book
        try {
            book = bookRepository.save(book);
            log.info("successfully Updated Book: bookID: " + bookId);
        } catch (Exception e) {
            log.error("Something went wrong while updating Book in bookRepository", e);
            throw new BadDataProvidedException(
                    "Something went wrong while updating Book in bookRepository" + e.getMessage());
        }
        log.info("Successfully Updated Book");
        return BookMapper.mapBook2DTO(book);
    }

    @Override
    public BookDTO updateAvailabilityOfBook(Integer bookId, Boolean availabilityStatus) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("Book not present for provided book id: " + bookId);
        }
        Book book = optionalBook.get();
        // if admin updated to make this book if this book is being rented available
        // throw error
        if (book.isAvailabilityStatus() == false && availabilityStatus == true) {
            throw new BadDataProvidedException(
                    "Unable to update Book's AvailabilityStatus as it is being hold by someone");
        }
        book.setAvailabilityStatus(availabilityStatus);
        // get the updated book
        book = bookRepository.save(book);
        return BookMapper.mapBook2DTO(book);
    }

    @Override
    public String deleteBook(Integer bookId) {
        log.info("Fetching Book from BookRepository with bookId: " + bookId);
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            log.error("Book not present for provided book id: " + bookId, BookNotFoundException.class);
            throw new BookNotFoundException("Book not present for provided book id: " + bookId);
        }

        Book book = optionalBook.get();
        // if book is being hold by a user we cant delete it
        log.info("Checking if book is rentedBy someone then cant delete");
        if (book.getRentedBy() != null) {
            log.error("Unable to delete book as it is being hold by an User: "
                    + book.getRentedBy().getEmail());
            throw new BadDataProvidedException("Unable to delete book as it is being hold by an User: "
                    + book.getRentedBy().getEmail());
        }

        bookRepository.delete(book);
        log.info("BookDeletedSucessfully bookId: " + book.getBookId());
        return "BookDeletedSucessfully";
    }

    @Override
    public BookDTO updateBook(Book book) {
        return BookMapper.mapBook2DTO(bookRepository.save(book));
    }

}
