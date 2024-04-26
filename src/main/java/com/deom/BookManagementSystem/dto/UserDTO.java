package com.deom.BookManagementSystem.dto;

import java.util.List;

import com.deom.BookManagementSystem.entity.Role;

import lombok.Builder;

@Builder
public class UserDTO {
    private Integer userId;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Role role;
    private List<String> book;

    public UserDTO() {
    }

    public UserDTO(Integer userId, String email, String firstName, String lastName, String password, Role role,
            List<String> book) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.book = book;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<String> getBook() {
        return book;
    }

    public void setBook(List<String> book) {
        this.book = book;
    }

}
