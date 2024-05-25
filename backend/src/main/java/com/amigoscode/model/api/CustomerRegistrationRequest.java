package com.amigoscode.model.api;

import com.amigoscode.model.enums.Gender;

public record CustomerRegistrationRequest(
        String name,
        String email,
        String password,
        Integer age,
        Gender gender
) {
}
