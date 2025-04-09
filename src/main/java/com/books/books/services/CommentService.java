package com.books.books.services;

import com.books.books.models.Comment;

import java.util.List;

public interface CommentService {
    public List<Comment> getAllComments();
    public void getCommentById(Long id);
    public void createComment(Comment comment);
    public void deleteComment(Long id);
    public void updateComment(Long id, Comment comment);
}
