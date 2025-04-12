package com.books.books.controllers;

import com.books.books.models.Book;
import com.books.books.models.Comment;
import com.books.books.models.User;
import com.books.books.repositories.BookRepository;
import com.books.books.repositories.CommentRepository;
import com.books.books.repositories.UserRepository;
import com.books.books.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/comments")
public class CommentController {


    @Autowired
    private CommentService commentService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/book/{bookId}")
//    @PreAuthorize("hasRole('USER')")
    public String addComment(
            @PathVariable Long bookId,
            @RequestParam("text") String text,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isEmpty()) {
            return "redirect:/books";
        }

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Comment comment = new Comment();
        comment.setText(text);
        comment.setBook(bookOpt.get());
        comment.setUser(user);

        commentService.createComment(comment);

        return "redirect:/books/" + bookId;
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('USER')")
    public String showEditForm(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty()) {
            return "redirect:/books";
        }

        Comment comment = commentOpt.get();

        if (!comment.getUser().getUsername().equals(userDetails.getUsername())) {
            return "redirect:/access-denied";
        }

        model.addAttribute("comment", comment);
        return "comments/edit";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('USER')")
    public String updateComment(
            @PathVariable Long id,
            @RequestParam("text") String newText,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty()) {
            return "redirect:/books";
        }

        Comment comment = commentOpt.get();

        if (!comment.getUser().getUsername().equals(userDetails.getUsername())) {
            return "redirect:/access-denied";
        }

        comment.setText(newText);
        commentService.updateComment(id, comment);

        return "redirect:/books/" + comment.getBook().getId();
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public String deleteComment(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty()) {
            return "redirect:/books";
        }

        Comment comment = commentOpt.get();

        if (!comment.getUser().getUsername().equals(userDetails.getUsername())) {
            return "redirect:/access-denied";
        }

        Long bookId = comment.getBook().getId();
        commentService.deleteComment(id);

        return "redirect:/books/" + bookId;
    }


}
