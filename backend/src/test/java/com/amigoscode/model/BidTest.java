package com.amigoscode.model;

import com.amigoscode.model.enums.ECurrency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BidTest {

    @Mock
    private Product mockProduct;

    @Mock
    private Customer mockCustomer;

    private double amount;
    private ECurrency currency;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        amount = 100.0;
        currency = ECurrency.USD;
    }

    @Test
    void testBidCreation() {
        Bid bid = new Bid(mockProduct, mockCustomer, amount, currency);

        assertNotNull(bid);
        assertEquals(mockProduct, bid.getProduct());
        assertEquals(mockCustomer, bid.getBuyer());
        assertEquals(amount, bid.getAmount());
        assertEquals(currency, bid.getCurrency());
        assertNotNull(bid.getDateAndTime());
    }

    @Test
    void testBidDefaultConstructor() {
        Bid bid = new Bid();
        assertNotNull(bid);
    }

    @Test
    void testGetId() {
        Bid bid = new Bid(mockProduct, mockCustomer, amount, currency);
        assertEquals(0, bid.getId());
    }

    @Test
    void testGetDateAndTime() {
        Bid bid = new Bid(mockProduct, mockCustomer, amount, currency);
        assertNotNull(bid.getDateAndTime());
    }

    @Test
    void testGetProduct() {
        Bid bid = new Bid(mockProduct, mockCustomer, amount, currency);
        assertEquals(mockProduct, bid.getProduct());
    }

    @Test
    void testGetBuyer() {
        Bid bid = new Bid(mockProduct, mockCustomer, amount, currency);
        assertEquals(mockCustomer, bid.getBuyer());
    }

    @Test
    void testGetAmount() {
        Bid bid = new Bid(mockProduct, mockCustomer, amount, currency);
        assertEquals(amount, bid.getAmount());
    }

    @Test
    void testGetCurrency() {
        Bid bid = new Bid(mockProduct, mockCustomer, amount, currency);
        assertEquals(currency, bid.getCurrency());
    }
}
