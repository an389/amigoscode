package com.amigoscode.persistance.interfaces.repository;

import com.amigoscode.model.Rating;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Transactional
public interface RatingRepository extends JpaRepository<Rating, Integer> {
    Page<Rating> findByProductIdOrderByDateAndTimeDesc(int productId, Pageable pageable);
}
