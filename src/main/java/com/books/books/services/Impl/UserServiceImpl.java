package com.books.books.services.Impl;

import com.books.books.enums.Roles;
import com.books.books.models.User;
import com.books.books.repositories.UserRepository;
import com.books.books.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "User not found with ID: " + id));
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(Long id, User user) {
        User userOld = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(
                        "User not found with ID: " + id));
        userOld.setUsername(user.getUsername());
        userOld.setEmail(user.getEmail());
        userOld.setPassword(user.getPassword());
        userOld.setRoles(user.getRoles());
        userOld.setComments(user.getComments());
        userRepository.save(userOld);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Cannot delete, user not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public boolean registerUser(String username, String email, String rawPassword) {
        if (userRepository.existsByUsername(username)) {
            return false;
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(rawPassword));
        newUser.setRoles(Roles.USER);

        userRepository.save(newUser);
        return true;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(
                        "User not found with username: " + username));
    }
}
