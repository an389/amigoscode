package com.amigoscode.model.api;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ProductRegistrationRequest(
        String email,
        String name,
        String description,
        double startingPrice,
        String currency,
        int sellerId,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime startDate,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime endDate
) {
}
