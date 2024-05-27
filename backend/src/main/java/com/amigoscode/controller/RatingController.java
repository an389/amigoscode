package com.amigoscode.controller;

import com.amigoscode.jwt.JWTUtil;
import com.amigoscode.model.Rating;
import com.amigoscode.model.api.RatingRegistrationRequest;
import com.amigoscode.model.dto.RatingDTO;
import com.amigoscode.service.RatingService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rating")
public class RatingController {
    private final RatingService ratingService;
    private final JWTUtil jwtUtil;

    public RatingController(RatingService ratingService, JWTUtil jwtUtil) {
        this.ratingService = ratingService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("{productId}")
    public List<RatingDTO> getRaitings(
            @PathVariable("productId") Integer productId){
        return ratingService.getAllRatings(productId, 0);
    }

    @PostMapping
    public ResponseEntity<?> registerRating(
            @RequestBody RatingRegistrationRequest request){
        ratingService.addRating(request);
        String jwtToken = jwtUtil.issueToken(request.email(), "ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }
}
