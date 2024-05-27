package com.amigoscode.persistance.mapper;

import com.amigoscode.model.Rating;
import com.amigoscode.model.dto.RatingDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RatingDTOMapper implements Function<Rating, RatingDTO> {

    @Override
    public RatingDTO apply(Rating rating) {
        return new RatingDTO(
                rating.getDateAndTime(),
                rating.getRatingUser().getName(),
                rating.getGrade(),
                rating.getComment()
        );
    }
}
