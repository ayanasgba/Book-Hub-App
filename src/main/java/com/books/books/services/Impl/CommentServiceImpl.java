package com.books.books.services.Impl;

import com.books.books.models.Comment;
import com.books.books.repositories.CommentRepository;
import com.books.books.services.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Override
    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void updateComment(Long id, Comment comment) {
        Comment commentOld = commentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(
                        "Comment not found with id: " + id));
        commentOld.setText(comment.getText());
        commentOld.setCreatedAt(comment.getCreatedAt());
        commentOld.setUser(comment.getUser());
        commentOld.setBook(comment.getBook());
        commentRepository.save(commentOld);
    }

    @Override
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Comment not found with id: " + id);
        }
        commentRepository.deleteById(id);
    }
}
