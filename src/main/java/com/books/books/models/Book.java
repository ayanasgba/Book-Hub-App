package com.books.books.models;

import com.books.books.enums.Genres;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String title;

    private String author;

    private String description;

    private String publicationDate;

    @Enumerated(EnumType.STRING)
    private Genres genres;

    @OneToMany(mappedBy = "book")
    private List<Comment> comments;

}
