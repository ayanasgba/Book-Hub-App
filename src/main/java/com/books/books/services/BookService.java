package com.books.books.services;

import com.books.books.models.Book;

import java.util.List;

public interface BookService {

    public List<Book> getAllBooks();
    public Book getBookById(Long id);
    public Book createBook(Book book);
    public void deleteBook(Long id);
    public Book updateBook(Long id, Book book);
    public void updateAverageRating(Book book);

//    List<Book> searchBooks(String title, String author);
    List<Book> searchBooks(String title, String author, String genre, Double minRating);

}
