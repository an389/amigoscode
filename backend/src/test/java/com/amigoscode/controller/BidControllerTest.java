package com.amigoscode.controller;

import com.amigoscode.jwt.JWTUtil;
import com.amigoscode.model.api.BidRegistrationRequest;
import com.amigoscode.model.dto.BidDTO;
import com.amigoscode.service.BidService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BidControllerTest {

    private BidService bidService;
    private JWTUtil jwtUtil;
    private BidController bidController;

    @BeforeEach
    public void setup() {
        bidService = Mockito.mock(BidService.class);
        jwtUtil = Mockito.mock(JWTUtil.class);
        bidController = new BidController(bidService, jwtUtil, null);
    }

    @Test
    public void testGetBids() {
        BidDTO bid = new BidDTO(
                1,
                "test@example.com",
                100.0,
                "Test Product",
                "Test Buyer",
                "USD",
                LocalDateTime.now()
        );        when(bidService.getBidsByProductId(1L)).thenReturn(Arrays.asList(bid));

        List<BidDTO> result = bidController.getBids(1L);

        assertEquals(1, result.size());
        assertEquals(bid, result.get(0));
    }

    @Test
    public void testRegisterBid() {
        BidRegistrationRequest request = new BidRegistrationRequest(
                100.0,
                1,
                "USD",
                1
        );
        bidController.registerBid(request);

        verify(bidService).addBid(request);
    }
}
