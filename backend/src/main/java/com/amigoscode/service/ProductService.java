package com.amigoscode.service;

import com.amigoscode.exception.ResourceNotFoundException;
import com.amigoscode.model.Customer;
import com.amigoscode.model.Product;
import com.amigoscode.model.api.ProductRegistrationRequest;
import com.amigoscode.model.dto.ProductDTO;
import com.amigoscode.model.enums.ECurrency;
import com.amigoscode.persistance.interfaces.CustomerDao;
import com.amigoscode.persistance.interfaces.ProductDAO;
import com.amigoscode.persistance.interfaces.repository.ProductRepository;
import com.amigoscode.persistance.mapper.ProductDTOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final CustomerDao customerDAO;
    private final ProductDAO productDAO;
    private final ProductDTOMapper productDTOMapper;
    private final ProductRepository productRepository;

    public ProductService(@Qualifier("jpaCustomer") CustomerDao customerDAO, @Qualifier("jpaProduct") ProductDAO productDAO, ProductDTOMapper productDTOMapper, ProductRepository productRepository) {
        this.customerDAO = customerDAO;
        this.productDAO = productDAO;
        this.productDTOMapper = productDTOMapper;
        this.productRepository = productRepository;
    }


    public void addProduct(ProductRegistrationRequest request) {
        Optional<Customer> customer = customerDAO.selectUserByEmail(request.sellerEmail());

        if (customer.isEmpty()) {
            throw new ResourceNotFoundException("please try again later");
        }

        Product product = new Product(
                request.name(),
                request.description(),
                request.startingPrice(),
                ECurrency.valueOf(request.currency()),
                customer.orElse(null),
                request.startDate(),
                request.endDate());

        productDAO.insertProduct(product);
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> product = productDAO.selectAllProducts()
                .stream().map(productDTOMapper)
                .collect(Collectors.toList());
        log.info("getAllProducts {}", product);
        return product;
    }

    public Product getProduct(Integer idProduct) {
        return productDAO.selectProductById(idProduct)
                .orElseThrow(() -> new ResourceNotFoundException("No product found!"));
    }

    public List<ProductDTO> findBySearch(String keyword) {
        return productRepository.findBySearch(keyword)
                .stream().map(productDTOMapper)
                .collect(Collectors.toList());
    }
}
