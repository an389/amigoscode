package com.amigoscode.service;

import com.amigoscode.exception.ResourceNotFoundException;
import com.amigoscode.model.Bid;
import com.amigoscode.model.dto.BidDTO;
import com.amigoscode.model.Customer;
import com.amigoscode.model.Product;
import com.amigoscode.model.api.BidRegistrationRequest;
import com.amigoscode.model.enums.ECurrency;
import com.amigoscode.persistance.interfaces.BidDAO;
import com.amigoscode.persistance.interfaces.CustomerDao;
import com.amigoscode.persistance.interfaces.ProductDAO;
import com.amigoscode.persistance.mapper.BidDTOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BidService {

    private static final Logger log = LoggerFactory.getLogger(BidService.class);
    private final BidDAO bidDAO;
    private final BidDTOMapper bidDTOMapper;
    private final CustomerDao customerDAO;
    private final ProductDAO productDAO;


    public BidService(@Qualifier("jpaBid") BidDAO bidDAO, BidDTOMapper bidDTOMapper, @Qualifier("jpaCustomer") CustomerDao customerDAO, @Qualifier("jpaProduct") ProductDAO productDAO) {

        this.bidDAO = bidDAO;
        this.bidDTOMapper = bidDTOMapper;
        this.customerDAO = customerDAO;
        this.productDAO = productDAO;
    }

    public List<BidDTO> getAllBids() {
        List<BidDTO> bids = bidDAO.selectAllBids().stream().map(bidDTOMapper).collect(Collectors.toList());
        log.info("getAllBids {}", bids);
        return bids;
    }

    public void addBid(BidRegistrationRequest request) {

        Optional<Customer> customer = customerDAO.selectCustomerById(request.userId());
        Optional<Product> product = productDAO.selectProductById(request.productId());

        if (customer.isEmpty()) {
            throw new ResourceNotFoundException("customer with id [%s] not found".formatted(request.userId()));
        }
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("product with id [%s] not found".formatted(request.productId()));
        }

        Bid bid = new Bid(product.orElse(null),
                customer.orElse(null),
                request.amount(),
                ECurrency.valueOf(request.currency()));

        bidDAO.insertBid(bid);
    }
}
