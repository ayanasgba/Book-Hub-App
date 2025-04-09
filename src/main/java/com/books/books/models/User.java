package com.books.books.models;

import com.books.books.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    @Email
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

}
