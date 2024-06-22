package com.amigoscode.persistance;

import com.amigoscode.model.Bid;
import com.amigoscode.model.Rating;
import com.amigoscode.persistance.interfaces.BidDAO;
import com.amigoscode.persistance.interfaces.repository.BidRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.amigoscode.constants.Constants.PAGE_SIZE;

@Repository("jpaBid")
public class BidJPADataAccessService implements BidDAO {

    private static final Logger log = LoggerFactory.getLogger(BidJPADataAccessService.class);
    private final BidRepository bidRepository;

    public BidJPADataAccessService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }


    @Override
    public List<Bid> selectAllBids(Long productId) {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE, Sort.by("dateAndTime").descending());
        Page<Bid> page = bidRepository.findByProductIdOrderByDateAndTimeDesc(productId, pageable);
        log.info("selectAllBids for bidId {}: {}", productId, page.getContent());
        return page.getContent();    }

    @Override
    public void insertBid(Bid bid) {
        bidRepository.save(bid);
        log.info("Save bid " + bid.toString());

    }
}
