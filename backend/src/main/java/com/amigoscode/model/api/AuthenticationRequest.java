package com.amigoscode.model.api;

public record AuthenticationRequest(
        String username,
        String password
) {
}
