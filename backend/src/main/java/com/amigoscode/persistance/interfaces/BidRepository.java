package com.amigoscode.persistance.interfaces;

import com.amigoscode.model.Bid;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface BidRepository extends JpaRepository<Bid, Integer> {

}
