package com.books.books.repositories;

import com.books.books.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT * FROM books WHERE author LIKE ?1",  nativeQuery = true)
    List<Book> getAllBooksByAuthor(String author);

    @Query(value = "SELECT * FROM books WHERE title LIKE ?1",  nativeQuery = true)
    List<Book> getAllBooksByTitle(String title);
}
