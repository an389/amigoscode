package com.amigoscode.model.api;

public record BidRegistrationRequest(
        double amount,
        int productId,
        String currency,
        int userId
) {
}
