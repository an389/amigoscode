package com.amigoscode.model.api;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
