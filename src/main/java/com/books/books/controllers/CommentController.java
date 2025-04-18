package com.books.books.controllers;

import com.books.books.models.Book;
import com.books.books.models.Comment;
import com.books.books.models.User;
import com.books.books.repositories.BookRepository;
import com.books.books.repositories.CommentRepository;
import com.books.books.repositories.UserRepository;
import com.books.books.services.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentService commentService,
                             BookRepository bookRepository,
                             UserRepository userRepository,
                             CommentRepository commentRepository) {
        this.commentService = commentService;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @PostMapping("/book/{bookId}")
    public String addComment(@PathVariable Long bookId,
                             @RequestParam("text") String text,
                             @AuthenticationPrincipal UserDetails userDetails) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userDetails.getUsername()));

        Comment comment = new Comment();
        comment.setText(text);
        comment.setBook(book);
        comment.setUser(user);
        commentService.createComment(comment);

        return "redirect:/books/" + bookId;
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('USER')")
    public String showEditForm(@PathVariable Long id,
                               Model model,
                               @AuthenticationPrincipal UserDetails userDetails) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + id));
        if (!comment.getUser().getUsername().equals(userDetails.getUsername())) {
            return "redirect:/access-denied";
        }
        model.addAttribute("comment", comment);
        return "comments/edit";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('USER')")
    public String updateComment(@PathVariable Long id,
                                @RequestParam("text") String newText,
                                @AuthenticationPrincipal UserDetails userDetails) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + id));
        if (!comment.getUser().getUsername().equals(userDetails.getUsername())) {
            return "redirect:/access-denied";
        }
        comment.setText(newText);
        commentService.updateComment(id, comment);

        return "redirect:/books/" + comment.getBook().getId();
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public String deleteComment(@PathVariable Long id,
                                @AuthenticationPrincipal UserDetails userDetails) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + id));
        if (!comment.getUser().getUsername().equals(userDetails.getUsername())) {
            return "redirect:/access-denied";
        }
        Long bookId = comment.getBook().getId();
        commentService.deleteComment(id);
        return "redirect:/books/" + bookId;
    }
}
