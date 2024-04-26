package com.deom.BookManagementSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.deom.BookManagementSystem.entity.Book;
import com.deom.BookManagementSystem.entity.User;
import com.deom.BookManagementSystem.repository.BookRepository;
import com.deom.BookManagementSystem.repository.UserRepository;
import com.deom.BookManagementSystem.service.bookService.BookServiceImpl;

@SpringBootTest(classes = { BookManagementSystemApplication.class })
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@DirtiesContext
@ActiveProfiles("test")
public class BookServiceImplTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book getBook() {
        // List<Book> books = new ArrayList<>();
        return new Book(1, "GOT", "A. F", "Fiction", true, null);
        // books.add(new Book(2, "GOT1", "A. F1", "Fiction", true, getUser()));
        // return books;
    }

    private User getUser() {
        return User.builder()
                .userId(1)
                .email("kj@gmail.com")
                .firstName("kj")
                .lastName("kj")
                .books(new ArrayList<>())
                .build();
    }

    @Test
    public void rentBookTest() {
        Optional<User> optionalUser = Optional.ofNullable(getUser());
        Optional<Book> optionalBook = Optional.ofNullable(getBook());

        String email = "kj@gmail.com";

        when(userRepository.findByEmail(email)).thenReturn(optionalUser);
        when(bookRepository.findById(1)).thenReturn(optionalBook);

        Book rentedBook = getBook();
        rentedBook.setAvailabilityStatus(false);
        rentedBook.setRentedBy(optionalUser.get());
        when(bookRepository.save(any(Book.class))).thenReturn(rentedBook);

        // assertFalse(bookService.rentBook(1, email).isAvailabilityStatus());
        assertEquals(bookService.rentBook(1, email).getUserEmail(), email);
    }
}
