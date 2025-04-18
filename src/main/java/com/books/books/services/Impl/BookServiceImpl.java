package com.books.books.services.Impl;

import com.books.books.enums.Genres;
import com.books.books.models.Book;
import com.books.books.models.Rating;
import com.books.books.repositories.BookRepository;
import com.books.books.repositories.RatingRepository;
import com.books.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private RatingRepository ratingRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, RatingRepository ratingRepository) {
        this.bookRepository = bookRepository;
        this.ratingRepository = ratingRepository;
    }
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Book not found with id: " + id));
    }
    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book book) {
        Book bookOld = bookRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(
                        "Book not found with id: " + id));
        bookOld.setTitle(book.getTitle());
        bookOld.setAuthor(book.getAuthor());
        bookOld.setDescription(book.getDescription());
        bookOld.setPublicationDate(book.getPublicationDate());
        bookOld.setGenres(book.getGenres());
        bookOld.setCoverFilename(book.getCoverFilename());
        return bookRepository.save(bookOld);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void updateAverageRating(Book book) {
        List<Rating> ratings = ratingRepository.findByBookId(book.getId());
        double avg = ratings.stream()
                .mapToInt(Rating::getPoint)
                .average()
                .orElse(0.0);
        book.setAverageRating(avg);
        bookRepository.save(book);
    }

    @Override
    public List<Book> searchBooks(String title, String author, String genreStr, Double minRating) {
        title = (title == null || title.isEmpty()) ? null : "%" + title + "%";
        author = (author == null || author.isEmpty()) ? null : "%" + author + "%";

        Genres genre = null;
        if (genreStr != null && !genreStr.isEmpty()) {
            try {
                genre = Genres.valueOf(genreStr);
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException("Invalid genre: " + genreStr, ex);
            }
        }

        return bookRepository.searchBooks(title, author, genre, minRating);
    }
}
