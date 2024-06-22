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
import com.amigoscode.persistance.s3.S3Buckets;
import com.amigoscode.persistance.s3.S3Service;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final CustomerDao customerDAO;
    private final ProductDAO productDAO;
    private final ProductDTOMapper productDTOMapper;
    private final ProductRepository productRepository;
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;

    public ProductService(@Qualifier("jpaCustomer") CustomerDao customerDAO, @Qualifier("jpaProduct") ProductDAO productDAO, ProductDTOMapper productDTOMapper, ProductRepository productRepository, S3Service s3Service, S3Buckets s3Buckets) {
        this.customerDAO = customerDAO;
        this.productDAO = productDAO;
        this.productDTOMapper = productDTOMapper;
        this.productRepository = productRepository;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
    }


    public Integer addProduct(ProductRegistrationRequest request) {
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

        int productId = productDAO.insertProduct(product);
        return productId;
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

    public void uploadProductImage(Integer productId, MultipartFile file) {
        productDAO.selectProductById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("No product found!"));
        try {
            s3Service.putObject(
                    s3Buckets.getCustomer(),
                    "product-images/%s/%s".formatted(productId, productId),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException("failed to upload image", e);
        }
    }

    public byte[] getProductImage(Integer productId) {
        productDAO.selectProductById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("No product found!"));

        byte[] profileImage = s3Service.getObject(
                s3Buckets.getCustomer(),
                "product-images/%s/%s".formatted(productId, productId));
        return profileImage;
    }
}
