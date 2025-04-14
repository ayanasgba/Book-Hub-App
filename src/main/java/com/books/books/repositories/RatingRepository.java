package com.books.books.repositories;

import com.books.books.models.Book;
import com.books.books.models.Rating;
import com.books.books.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByBookId(Long bookId);
    Optional<Rating> findByUserAndBook(User user, Book book);
}
