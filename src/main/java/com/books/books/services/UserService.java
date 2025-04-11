package com.books.books.services;

import com.books.books.models.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();
    public User getUserById(Long id);
    public void createUser(User user);
    public void deleteUser(Long id);
    public void updateUser(Long id, User user);
    public boolean registerUser(String username, String email, String rawPassword);
}
