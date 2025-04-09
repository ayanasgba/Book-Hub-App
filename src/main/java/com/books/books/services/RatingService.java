package com.books.books.services;

import com.books.books.models.Rating;

import java.util.List;

public interface RatingService {
    public List<Rating> getAllRating();
    public void createRating(Rating rating);
    public void deleteRating(Long id);
    public void updateRating(Long id, Rating rating);
}
