package com.amigoscode.service;

import com.amigoscode.exception.ResourceNotFoundException;
import com.amigoscode.model.Customer;
import com.amigoscode.model.Product;
import com.amigoscode.model.api.ProductRegistrationRequest;
import com.amigoscode.model.enums.ECurrency;
import com.amigoscode.persistance.interfaces.CustomerDao;
import com.amigoscode.persistance.interfaces.ProductDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final CustomerDao customerDAO;
    private final ProductDAO productDAO;

    public ProductService(@Qualifier("jpaCustomer") CustomerDao customerDAO, @Qualifier("jpaProduct") ProductDAO productDAO) {
        this.customerDAO = customerDAO;
        this.productDAO = productDAO;
    }


    public void addProduct(ProductRegistrationRequest request) {
        Optional<Customer> customer = customerDAO.selectCustomerById(request.sellerId());

        if (customer.isEmpty()) {
            throw new ResourceNotFoundException("customer with id [%s] not found".formatted(request.sellerId()));
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
}
