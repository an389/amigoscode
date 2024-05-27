package com.amigoscode.model.dto;

import java.time.LocalDateTime;

public record RatingDTO(
        LocalDateTime dateAndTime,
        String ratingCustomerName,
        int grade,
        String comment
) {
}
