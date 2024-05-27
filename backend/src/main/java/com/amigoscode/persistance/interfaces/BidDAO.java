package com.amigoscode.persistance.interfaces;

import com.amigoscode.model.Bid;

import java.util.List;

public interface BidDAO {
    List<Bid> selectAllBids();
    void insertBid(Bid bid);
}
