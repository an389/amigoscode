package com.amigoscode.persistance.mapper;

import com.amigoscode.model.Product;
import com.amigoscode.model.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductDTOMapper implements Function<Product, ProductDTO> {
    @Override
    public ProductDTO apply(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getStartingPrice(),
                product.getCurrency().toString(),
                product.getSeller().getId(),
                product.getSeller().getName(),
                product.getCreationDate(),
                product.getStartDate(),
                product.getEndDate()
        );
    }
}
