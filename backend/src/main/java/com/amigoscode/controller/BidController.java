package com.amigoscode.controller;

import com.amigoscode.model.dto.BidDTO;
import com.amigoscode.model.api.BidRegistrationRequest;
import com.amigoscode.service.BidService;
import com.amigoscode.jwt.JWTUtil;
import com.amigoscode.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bids")
public class BidController {
    private static final Logger log = LoggerFactory.getLogger(BidController.class);
    private final BidService bidService;
    private final JWTUtil jwtUtil;

    public BidController(BidService bidService,
                         JWTUtil jwtUtil, CustomerService customerService) {
        this.bidService = bidService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<BidDTO> getBids() {
        return bidService.getAllBids();
    }

    @PostMapping
    public void registerBid(
            @RequestBody BidRegistrationRequest request) {
        bidService.addBid(request);
    }
}
