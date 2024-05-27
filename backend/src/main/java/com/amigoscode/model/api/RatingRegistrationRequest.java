package com.amigoscode.model.api;

public record RatingRegistrationRequest(
        String email,
        int productId,
        int idRatingCustomer,
        int idRatedCustomer,
        int grade,
        String comment
) {
}
