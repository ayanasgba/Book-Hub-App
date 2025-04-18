package com.books.books.services;

import com.books.books.models.Book;
import java.util.List;

public interface BookService {

    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book createBook(Book book);
    Book updateBook(Long id, Book book);
    void deleteBook(Long id);
    void updateAverageRating(Book book);
    List<Book> searchBooks(String title, String author, String genre, Double minRating);

}
