package com.deom.BookManagementSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.deom.BookManagementSystem.dto.UserDTO;
import com.deom.BookManagementSystem.entity.User;
import com.deom.BookManagementSystem.mapper.UserMapper;
import com.deom.BookManagementSystem.repository.UserRepository;
import com.deom.BookManagementSystem.service.bookService.BookService;
import com.deom.BookManagementSystem.service.userService.UserServiceImpl;

@SpringBootTest(classes = { BookManagementSystemApplication.class })
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@DirtiesContext
@ActiveProfiles("test")
public class UserServiceTest {
    public static final String userEndPoint = "/users";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BookService bookService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .userId(1)
                .email("kj@gmail.com")
                .firstName("kj")
                .lastName("kj")
                .books(new ArrayList<>())
                .build());

        users.add(User.builder()
                .userId(2)
                .email("kj1@gmail.com")
                .firstName("kj1")
                .lastName("kj1")
                .books(new ArrayList<>())
                .build());
        return users;

    }

    @Test
    public void getAllUsers() {
        List<User> users = getUsers();
        when(userRepository.findAll()).thenReturn(users);

        assertEquals(users.stream().map(user -> UserMapper.mapUser2UserDTO(user))
                .collect(Collectors.toList()).size(), userService.getUsers().size());

        verify(userRepository, times(1)).findAll();

    }

    @Test
    public void getUser() {
        String email = "kj@gmail.com";
        User user = getUsers().get(0);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDTO userDTO = UserMapper.mapUser2UserDTO(user);
        assertEquals(userService.getUser(email).getFirstName(), userDTO.getFirstName());
        verify(userRepository, times(1)).findByEmail(email);
    }
}
