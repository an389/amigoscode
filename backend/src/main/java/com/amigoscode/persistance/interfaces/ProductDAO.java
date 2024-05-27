package com.amigoscode.persistance.interfaces;

import com.amigoscode.model.Customer;
import com.amigoscode.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    Optional<Product> selectProductById(Integer productId);

    void insertProduct(Product product);

    List<Product> selectAllProducts();
}
