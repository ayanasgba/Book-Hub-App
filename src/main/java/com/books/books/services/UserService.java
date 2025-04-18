package com.books.books.services;

import com.books.books.models.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    void createUser(User user);
    void updateUser(Long id, User user);
    void deleteUser(Long id);
    boolean registerUser(String username, String email, String rawPassword);
    User findByUsername(String username);
}
