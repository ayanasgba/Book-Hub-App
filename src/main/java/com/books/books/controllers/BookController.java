package com.books.books.controllers;

import com.books.books.models.Book;
import com.books.books.services.BookService;
import com.books.books.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private BookService bookService;
    private final FileStorageService fileStorageService;

    @Autowired
    public BookController (BookService bookService,
                           FileStorageService fileStorageService) {
        this.bookService = bookService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(required = false) String title,
                              @RequestParam(required = false) String author,
                              @RequestParam(required = false) String genre,
                              @RequestParam(required = false) Double minRating,
                              Model model) {
        List<Book> books = bookService.searchBooks(title, author, genre, minRating);
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/{id}")
    public String viewBook(@PathVariable Long id,
                           Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        if (userDetails != null) {
            model.addAttribute("currentUsername", userDetails.getUsername());
        }
        return "books/details";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/form";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book,
                           @RequestParam("coverImage") MultipartFile coverImage) throws IOException {
        Book saved = bookService.createBook(book);
        if (coverImage != null && !coverImage.isEmpty()) {
            String filename = fileStorageService.storeFile(coverImage, saved.getId());
            saved.setCoverFilename(filename);
            bookService.updateBook(saved.getId(), saved);
        }
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "books/form";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id,
                             @ModelAttribute Book book,
                             @RequestParam("coverImage") MultipartFile coverImage) throws IOException {
        bookService.updateBook(id, book);
        if (coverImage != null && !coverImage.isEmpty()) {
            String filename = fileStorageService.storeFile(coverImage, id);
            book.setCoverFilename(filename);
            bookService.updateBook(id, book);
        }
        return "redirect:/books";
    }

    @DeleteMapping("/delete/{id}")
    public String  deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
