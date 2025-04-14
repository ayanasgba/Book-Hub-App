package com.books.books.repositories;

import com.books.books.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT * FROM books WHERE author LIKE ?1",  nativeQuery = true)
    List<Book> getAllBooksByAuthor(String author);

    @Query(value = "SELECT * FROM books WHERE title LIKE ?1",  nativeQuery = true)
    List<Book> getAllBooksByTitle(String title);

    @Query(value = "SELECT * FROM books WHERE title LIKE ?1 AND author LIKE ?2", nativeQuery = true)
    List<Book> getAllBooksByTitleAndAuthor(String title, String author);

    @Query("""
    SELECT b FROM Book b
    WHERE (:title IS NULL OR b.title LIKE :title)
      AND (:author IS NULL OR b.author LIKE :author)
      AND (:genre IS NULL OR b.genres = :genre)
      AND (:minRating IS NULL OR b.averageRating >= :minRating)
    """)
    List<Book> searchBooks(@Param("title") String title,
                           @Param("author") String author,
                           @Param("genre") com.books.books.enums.Genres genre,
                           @Param("minRating") Double minRating);

}
