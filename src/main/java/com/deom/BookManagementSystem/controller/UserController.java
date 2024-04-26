package com.deom.BookManagementSystem.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deom.BookManagementSystem.dto.UserDTO;
import com.deom.BookManagementSystem.exception.BadDataProvidedException;
import com.deom.BookManagementSystem.exchanges.ChangePasswordRequest;
import com.deom.BookManagementSystem.exchanges.UpdateUserRequest;
import com.deom.BookManagementSystem.service.userService.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok().body("Hello My First Authentication Project");
    }

    @GetMapping("/allUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDTO> getAllUsers() {
        log.info("Get all Users: Only By ADMIN");
        return userService.getUsers();
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public UserDTO getUser(Principal principal) {
        log.info("Get LoggedIn User Request");
        return userService.getUser(principal.getName());
    }

    @GetMapping("/user/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDTO getUser(@PathVariable String email) {
        log.info("Get User by userEmail : Only By ADMIN");
        return userService.getUser(email);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PutMapping("/user/changePassword")
    public UserDTO changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
            HttpServletRequest httpServletRequest, Principal principal) {
        log.info("Change User Password request: ");
        if (changePasswordRequest == null || changePasswordRequest.getPassword().isBlank()) {
            log.error("Unable to change password : Bad data provided", BadDataProvidedException.class);
            throw new BadDataProvidedException("Unable to change password : Bad data provided");
        }

        UserDTO updatedUserDTO = userService.changePassword(principal.getName(), changePasswordRequest);
        log.info("Successfully Changed Password");
        return updatedUserDTO;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PutMapping("/user/update")
    public UserDTO updateUser(@RequestBody UpdateUserRequest updateUserRequest, Principal principal) {
        log.info("Update User Request: ");
        if (updateUserRequest == null || updateUserRequest.getFirstName() == null
                || updateUserRequest.getFirstName().isBlank() || updateUserRequest.getLastName() == null
                || updateUserRequest.getLastName().isBlank()) {
            log.error("Unable to update user : Bad data provided", BadDataProvidedException.class);
            throw new BadDataProvidedException("Unable to update user : Bad data provided");
        }
        return userService.updateUser(principal.getName(), updateUserRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PutMapping("/user/deleteUser")
    public UserDTO deleteUser(Principal principal) {
        return userService.deleteUser(principal.getName());
    }

}
