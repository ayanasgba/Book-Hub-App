package com.books.books.controllers;

import com.books.books.models.Book;
import com.books.books.models.Rating;
import com.books.books.models.User;
import com.books.books.repositories.BookRepository;
import com.books.books.repositories.RatingRepository;
import com.books.books.services.BookService;
import com.books.books.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingRepository ratingRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final UserService userService;

    @PostMapping("/book/{bookId}")
    public String rateBook(@PathVariable Long bookId,
                           @RequestParam int point,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {

        if (point < 1 || point > 5) {
            model.addAttribute("error", "Rate from 1 to 5");
            return "error";
        }

        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            model.addAttribute("error", "Book not found");
            return "error";
        }

        Book book = optionalBook.get();

        Rating rating = ratingRepository.findByUserAndBook(user, book).orElse(new Rating());
        rating.setUser(user);
        rating.setBook(book);
        rating.setPoint(point);
        ratingRepository.save(rating);

        bookService.updateAverageRating(book);

        return "redirect:/books/" + bookId;
    }

}
