package com.amigoscode.persistance.interfaces.repository;

import com.amigoscode.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findById(Integer productId);
    @Query("from Product p where p.name like %:keyword%")
    List<Product> findBySearch (@Param("keyword") String keyword);
    @Query("from Product p where p.endDate > local date")
    List<Product> selectAllProducts();
}
