package com.amigoscode.model.dto;

import java.time.LocalDateTime;

public record BidDTO(
        Integer id,
        String email,
        double amount,
        String productName,
        String buyerName,
        String currency,
        LocalDateTime dateAndTime
) {
}
