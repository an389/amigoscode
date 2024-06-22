package com.amigoscode.persistance.interfaces.repository;

import com.amigoscode.model.Bid;
import com.amigoscode.model.Rating;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface BidRepository extends JpaRepository<Bid, Integer> {

    Page<Bid> findByProductIdOrderByDateAndTimeDesc(Long productId, Pageable pageable);
}
