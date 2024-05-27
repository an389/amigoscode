package com.amigoscode.model.dto;

import java.time.LocalDateTime;

public record ProductDTO(
        Integer id,
        String name,
        String description,
        double startingPrice,
        String currency,
        Integer sellerId,
        String sellerName,
        LocalDateTime creationDate,
        LocalDateTime startDate,
        LocalDateTime endDate

) {
}
