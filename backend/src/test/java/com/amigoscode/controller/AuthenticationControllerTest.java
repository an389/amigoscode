package com.amigoscode.controller;

import com.amigoscode.controller.AuthenticationController;
import com.amigoscode.model.api.AuthenticationRequest;
import com.amigoscode.model.api.AuthenticationResponse;
import com.amigoscode.model.dto.CustomerDTO;
import com.amigoscode.model.enums.Gender;
import com.amigoscode.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AuthenticationControllerTest {

    private AuthenticationService authenticationService;
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setup() {
        authenticationService = Mockito.mock(AuthenticationService.class);
        authenticationController = new AuthenticationController(authenticationService);
    }

    @Test
    public void testLogin() {
        AuthenticationRequest request = new AuthenticationRequest("testUser", "testPassword");
        AuthenticationResponse response = new AuthenticationResponse("testToken", new CustomerDTO(
                1, // id
                "Test Name", // name
                "test@example.com", // email
                Gender.MALE, // gender
                30, // age
                Arrays.asList("ROLE_USER"), // roles
                "testUser", // username
                "1" // profileImageId
        ));
        when(authenticationService.login(request)).thenReturn(response);

        ResponseEntity<?> result = authenticationController.login(request);

        assertEquals(response, result.getBody());
        assertEquals("testToken", result.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
    }
}