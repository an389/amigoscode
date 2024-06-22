package com.amigoscode.persistance;

import com.amigoscode.model.Product;
import com.amigoscode.persistance.interfaces.ProductDAO;
import com.amigoscode.persistance.interfaces.repository.ProductRepository;
import org.slf4j.LoggerFactory;
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
    public int insertProduct(Product product) {
        log.info("Save product: " + product.toString());
        productRepository.save(product);
        return product.getId();
    }

    @Override
    public List<Product> selectAllProducts() {
        List<Product> allProducts = productRepository.selectAllProducts();
        log.info("Product page {}", allProducts);
        return allProducts;
    }
}
