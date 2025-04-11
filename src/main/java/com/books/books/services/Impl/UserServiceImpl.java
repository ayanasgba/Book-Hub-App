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

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void getUserById(Long id) {
        userRepository.findById(id);
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(Long id, User user) {
        User userOld = userRepository.findById(id).orElseThrow(()-> new RuntimeException());
        userOld.setUsername(user.getUsername());
        userOld.setEmail(user.getEmail());
        userOld.setPassword(user.getPassword());
        userOld.setRoles(user.getRoles());
        userOld.setComments(user.getComments());
        userRepository.save(userOld);
    }

    @Override
    public boolean registerUser(String username, String email, String rawPassword) {
        if (userRepository.existsByUsername(username)) return false;

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(Roles.USER);

        userRepository.save(user);
        return true;
    }
}
