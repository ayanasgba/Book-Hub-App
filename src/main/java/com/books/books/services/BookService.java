package com.books.books.services;

import com.books.books.models.Book;

import java.util.List;

public interface BookService {
    public List<Book> getAllBook();
    public void createBook(Book book);
    public void deleteBook(Long id);
    public void updateBook(Long id, Book book);
}
