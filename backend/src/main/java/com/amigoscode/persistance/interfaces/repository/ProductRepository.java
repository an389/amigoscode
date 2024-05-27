package com.amigoscode.persistance.interfaces.repository;

import com.amigoscode.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findById(Integer productId);
}
