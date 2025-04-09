package com.books.books.services.Impl;

import com.books.books.models.Comment;
import com.books.books.repositories.CommentRepository;
import com.books.books.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public void getCommentById(Long id) {
        commentRepository.findById(id);
    }

    @Override
    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void updateComment(Long id, Comment comment) {
        Comment commentOld = commentRepository.findById(id).orElseThrow(()-> new RuntimeException());
        commentOld.setText(comment.getText());
        commentOld.setCreatedAt(comment.getCreatedAt());
        commentOld.setUser(comment.getUser());
        commentOld.setBook(comment.getBook());
        commentRepository.save(commentOld);
    }
}
