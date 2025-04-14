package com.books.books.services.Impl;

import com.books.books.models.Rating;
import com.books.books.repositories.RatingRepository;
import com.books.books.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }
    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public void getRatingById(Long id) {
        ratingRepository.findById(id);
    }

    @Override
    public void createRating(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public void updateRating(Long id, Rating rating) {
        Rating ratingOld = ratingRepository.findById(id).orElseThrow(()-> new RuntimeException());
        ratingOld.setPoint(rating.getPoint());
        ratingOld.setUser(rating.getUser());
        ratingOld.setBook(rating.getBook());
        ratingRepository.save(ratingOld);
    }

}
