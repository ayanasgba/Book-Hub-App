package com.books.books.controllers;

import com.books.books.models.Book;
import com.books.books.models.Rating;
import com.books.books.models.User;
import com.books.books.repositories.BookRepository;
import com.books.books.repositories.RatingRepository;
import com.books.books.services.BookService;
import com.books.books.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ratings")
public class RatingController {

    private final RatingRepository ratingRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public RatingController(RatingRepository ratingRepository,
                            BookRepository bookRepository,
                            BookService bookService,
                            UserService userService) {
        this.ratingRepository = ratingRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    @PostMapping("/book/{bookId}")
    public String rateBook(@PathVariable Long bookId,
                           @RequestParam int point,
                           @AuthenticationPrincipal UserDetails userDetails) {
        if (point < 1 || point > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        User user = userService.findByUsername(userDetails.getUsername());
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));
        Rating rating = ratingRepository.findByUserAndBook(user, book)
                .orElse(new Rating());
        rating.setUser(user);
        rating.setBook(book);
        rating.setPoint(point);
        ratingRepository.save(rating);

        bookService.updateAverageRating(book);

        return "redirect:/books/" + bookId;
    }
}
