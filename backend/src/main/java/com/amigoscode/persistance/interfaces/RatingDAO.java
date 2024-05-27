package com.amigoscode.persistance.interfaces;

import com.amigoscode.model.Rating;

import java.util.List;

public interface RatingDAO {
    List<Rating> selectAllRatings(int productId, int pageNumber);
    void insertRating(Rating rating);
}
