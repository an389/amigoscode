package com.amigoscode.model.api;

public record BidRegistrationRequest(
        String email,
        Integer amount
) {
}
