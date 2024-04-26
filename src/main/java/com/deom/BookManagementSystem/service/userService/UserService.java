package com.deom.BookManagementSystem.service.userService;

import java.util.List;

import com.deom.BookManagementSystem.dto.UserDTO;
import com.deom.BookManagementSystem.entity.User;
import com.deom.BookManagementSystem.exchanges.ChangePasswordRequest;
import com.deom.BookManagementSystem.exchanges.UpdateUserRequest;

public interface UserService {
    public List<UserDTO> getUsers();

    public UserDTO getUser(String email);

    public User getUserObject(String email);

    public UserDTO createUser(User user);

    public UserDTO changePassword(String email, ChangePasswordRequest changePasswordRequest);

    public UserDTO updateUser(String email, UpdateUserRequest updateUserRequest);

    public UserDTO deleteUser(String email);
}
