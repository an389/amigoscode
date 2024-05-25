package com.amigoscode.model.api;

import com.amigoscode.model.CustomerDTO;

public record AuthenticationResponse (
        String token,
        CustomerDTO customerDTO){
}
