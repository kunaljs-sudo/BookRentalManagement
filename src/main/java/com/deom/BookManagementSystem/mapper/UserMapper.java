package com.deom.BookManagementSystem.mapper;

import java.util.stream.Collectors;

import com.deom.BookManagementSystem.dto.UserDTO;
import com.deom.BookManagementSystem.entity.User;

public class UserMapper {
    public static UserDTO mapUser2UserDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .book(user.getBooks().stream().map(b -> b.getTitle()).collect(Collectors.toList()))
                .build();

    }
}
