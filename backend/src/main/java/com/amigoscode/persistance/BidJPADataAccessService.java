package com.amigoscode.persistance;

import com.amigoscode.model.Bid;
import com.amigoscode.persistance.interfaces.BidDAO;
import com.amigoscode.persistance.interfaces.repository.BidRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("jpaBid")
public class BidJPADataAccessService implements BidDAO {

    private static final Logger log = LoggerFactory.getLogger(BidJPADataAccessService.class);
    private final BidRepository bidRepository;

    public BidJPADataAccessService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    @Override
    public List<Bid> selectAllBids() {
        Page<Bid> page = bidRepository.findAll(Pageable.ofSize(10));
        log.info("page " + page.getContent());
        return page.getContent();
    }

    @Override
    public void insertBid(Bid bid) {
        log.info("Save bid " + bid.toString());
        bidRepository.save(bid);
    }
}
