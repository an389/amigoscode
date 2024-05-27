package com.amigoscode.model.api;

import com.amigoscode.model.dto.CustomerDTO;

public record AuthenticationResponse (
        String token,
        CustomerDTO customerDTO){
}
