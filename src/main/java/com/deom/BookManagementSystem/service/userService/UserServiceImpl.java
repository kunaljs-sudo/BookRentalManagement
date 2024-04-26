package com.deom.BookManagementSystem.service.userService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deom.BookManagementSystem.dto.UserDTO;
import com.deom.BookManagementSystem.entity.Book;
import com.deom.BookManagementSystem.entity.User;
import com.deom.BookManagementSystem.exception.BadDataProvidedException;
import com.deom.BookManagementSystem.exception.UserNameNoFoundException;
import com.deom.BookManagementSystem.exchanges.ChangePasswordRequest;
import com.deom.BookManagementSystem.exchanges.UpdateUserRequest;
import com.deom.BookManagementSystem.mapper.UserMapper;
import com.deom.BookManagementSystem.repository.UserRepository;
import com.deom.BookManagementSystem.service.bookService.BookService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BookService bookService;

    @Override
    public List<UserDTO> getUsers() {
        log.info("Fecthing All Users from UserRepository");
        return userRepository.findAll().stream().map(user -> UserMapper.mapUser2UserDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUser(String email) {
        log.info("Fecthing user from UserRepository");
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            log.error("User email not found: " + email, UserNameNoFoundException.class);
            throw new UserNameNoFoundException("User email not found: " + email);
        }
        log.info("User data retrieved");
        return UserMapper.mapUser2UserDTO(optionalUser.get());
    }

    @Override
    public User getUserObject(String email) {
        log.info("Fecthing user from UserRepository");
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            log.error("User email not found: " + email, UserNameNoFoundException.class);
            throw new UserNameNoFoundException("User email not found: " + email);
        }
        log.info("User data retrieved");
        return optionalUser.get();
    }

    @Override
    public UserDTO createUser(User user) {
        if (user == null || user.getUserId() != null) {
            throw new BadDataProvidedException(
                    "While creating User...either user object is null or user_id is not null");
        }
        User createdUser = userRepository.save(user);
        return UserMapper.mapUser2UserDTO(createdUser);

    }

    public UserDTO changePassword(String email, ChangePasswordRequest changePasswordRequest) {
        log.info("Fecthing user from this.getUserObject(email)");
        User user = getUserObject(email);
        if (changePasswordRequest.getPassword() != null && !changePasswordRequest.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
        }
        try {
            UserDTO userDTO = UserMapper.mapUser2UserDTO(userRepository.save(user));
            log.info("User password has been changed successfully");
            return userDTO;
        } catch (Exception e) {
            log.error("Something went wrong while saving user Object", e);
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public UserDTO updateUser(String email, UpdateUserRequest updateUserRequest) {
        User user = getUserObject(email);
        if (updateUserRequest.getFirstName() != null && !updateUserRequest.getFirstName().isBlank()) {
            user.setFirstName(updateUserRequest.getFirstName());
        }
        if (updateUserRequest.getLastName() != null && !updateUserRequest.getLastName().isBlank()) {
            user.setLastName(updateUserRequest.getLastName());
        }

        return UserMapper.mapUser2UserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO deleteUser(String email) {
        log.info("Fecthing user from this.getUserObject(email)");
        User user = this.getUserObject(email);
        log.info("Returning All Books which user has rented");
        if (user.getBooks().size() > 0) {
            for (Book book : user.getBooks()) {
                bookService.returnBook(book.getBookId(), user.getEmail());
            }
        }
        log.info("Get Updated User from this.getUserObject(email)");
        user = this.getUserObject(email);
        try {
            userRepository.delete(user);
            log.info("User deleted Successfully");
            return UserMapper.mapUser2UserDTO(user);
        } catch (Exception ex) {
            log.error("Bad data provided ", ex);
            throw new BadDataProvidedException("Bad data provided " + ex.getMessage());
        }
    }

}
