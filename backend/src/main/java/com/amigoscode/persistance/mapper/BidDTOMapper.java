package com.amigoscode.persistance.mapper;

import com.amigoscode.model.Bid;
import com.amigoscode.model.dto.BidDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BidDTOMapper implements Function<Bid, BidDTO> {
    @Override
    public BidDTO apply(Bid bid) {
        return new BidDTO(
                bid.getId(),
                bid.getBuyer().getEmail(),
                bid.getAmount(),
                bid.getProduct().getName(),
                bid.getBuyer().getName(),
                bid.getCurrency().toString(),
                bid.getDateAndTime()
        );
    }
}
