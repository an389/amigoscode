package com.amigoscode.persistance.interfaces;

import com.amigoscode.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    Optional<Product> selectProductById(Integer productId);
    int insertProduct(Product product);
    List<Product> selectAllProducts();
}
