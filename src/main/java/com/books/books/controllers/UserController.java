package com.books.books.controllers;

import com.books.books.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/users";
    }

}
