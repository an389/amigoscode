package com.amigoscode.persistance.interfaces.repository;

import com.amigoscode.model.Customer;
import com.amigoscode.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsCustomerByEmail(String email);
    boolean existsCustomerById(Integer id);
    Optional<Customer> findCustomerByEmail(String email);
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Customer c SET c.profileImageId = ?1 WHERE c.id = ?2")
    int updateProfileImageId(String profileImageId, Integer customerId);

    @Query("from Customer c where c.name like %:keyword%")
    List<Customer> findBySearch (@Param("keyword") String keyword);}
