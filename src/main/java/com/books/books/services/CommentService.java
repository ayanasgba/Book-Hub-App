package com.books.books.services;

import com.books.books.models.Comment;

public interface CommentService {
    void createComment(Comment comment);
    void updateComment(Long id, Comment comment);
    void deleteComment(Long id);
}
