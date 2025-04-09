package com.books.books.services.Impl;

import com.books.books.models.Book;
import com.books.books.repositories.BookRepository;
import com.books.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with id " + id + " is not found"));
    }
    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book updateBook(Long id, Book book) {
        Book bookOld = bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Book with id " + id + " is not found"));
        bookOld.setTitle(book.getTitle());
        bookOld.setAuthor(book.getAuthor());
        bookOld.setDescription(book.getDescription());
        bookOld.setPublicationDate(book.getPublicationDate());
        bookOld.setGenres(book.getGenres());
        bookOld.setComments(book.getComments());
        return bookRepository.save(bookOld);
    }
}
