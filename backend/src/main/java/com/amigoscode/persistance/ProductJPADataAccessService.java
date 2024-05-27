package com.amigoscode.persistance;

import ch.qos.logback.classic.Logger;
import com.amigoscode.model.Bid;
import com.amigoscode.model.Product;
import com.amigoscode.persistance.interfaces.ProductDAO;
import com.amigoscode.persistance.interfaces.repository.ProductRepository;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpaProduct")
public class ProductJPADataAccessService implements ProductDAO {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ProductJPADataAccessService.class);
    private final ProductRepository productRepository;

    public ProductJPADataAccessService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> selectProductById(Integer productId) {
        return productRepository.findById(productId);
    }

    @Override
    public void insertProduct(Product product) {
        log.info("Save product: " + product.toString());
        productRepository.save(product);
    }

    @Override
    public List<Product> selectAllProducts() {
        Page<Product> page = productRepository.findAll(Pageable.ofSize(1000));
        log.info("Product page " + page.getContent());
        return page.getContent();
    }
}
