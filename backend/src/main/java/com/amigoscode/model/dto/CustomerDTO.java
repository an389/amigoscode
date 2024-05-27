package com.amigoscode.model.dto;

import com.amigoscode.model.enums.Gender;

import java.util.List;

public record CustomerDTO(
        Integer id,
        String name,
        String email,
        Gender gender,
        Integer age,
        List<String> roles,
        String username,
        String profileImageId
) {

}
