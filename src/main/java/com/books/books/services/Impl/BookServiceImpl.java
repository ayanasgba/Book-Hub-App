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
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public void createBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void updateBook(Long id, Book book) {
        Book bookOld = bookRepository.findById(id).orElseThrow(()-> new RuntimeException());
        bookOld.setTitle(book.getTitle());
        bookOld.setAuthor(book.getAuthor());
        bookOld.setDescription(book.getDescription());
        bookOld.setPublicationDate(book.getPublicationDate());
        bookOld.setGenres(book.getGenres());
        bookOld.setComments(book.getComments());
        bookRepository.save(bookOld);
    }
}
