package com.amigoscode.model.api;

public record BidRegistrationRequest(
        String email,
        double amount,
        int productId,
        String currency,
        int userId
) {
}
