package com.amigoscode.service;

import com.amigoscode.exception.ResourceNotFoundException;
import com.amigoscode.model.Customer;
import com.amigoscode.model.Product;
import com.amigoscode.model.Rating;
import com.amigoscode.model.api.RatingRegistrationRequest;
import com.amigoscode.model.dto.RatingDTO;
import com.amigoscode.persistance.interfaces.CustomerDao;
import com.amigoscode.persistance.interfaces.ProductDAO;
import com.amigoscode.persistance.interfaces.RatingDAO;
import com.amigoscode.persistance.mapper.RatingDTOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingService {

    private static final Logger log = LoggerFactory.getLogger(RatingService.class);
    private final ProductDAO productDAO;
    private final CustomerDao customerDao;
    private final RatingDAO ratingDAO;
    private final RatingDTOMapper ratingDTOMapper;

    public RatingService(@Qualifier("jpaProduct")ProductDAO productDAO, @Qualifier("jpaCustomer") CustomerDao customerDao, @Qualifier("jpaRating") RatingDAO ratingDAO, RatingDTOMapper ratingDTOMapper) {
        this.productDAO = productDAO;
        this.customerDao = customerDao;
        this.ratingDAO = ratingDAO;
        this.ratingDTOMapper = ratingDTOMapper;
    }

    public List<RatingDTO> getAllRatings(int productId, int page){
        return ratingDAO.selectAllRatings(productId, page)
                .stream()
                .map(ratingDTOMapper)
                .collect(Collectors.toList());
    }

    public void addRating(RatingRegistrationRequest request){
        Optional<Customer> ratingCustomer = customerDao.selectUserByEmail(request.email());
        Optional<Customer> ratedCustomer = customerDao.selectCustomerById(request.idRatedCustomer());
        Optional<Product> product = productDAO.selectProductById(request.productId());

        if (ratingCustomer.isEmpty()) {
            throw new ResourceNotFoundException("RatingCustomer not found");
        }
        if (ratedCustomer.isEmpty()) {
            throw new ResourceNotFoundException("RatedCustomer not found");
        }
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }

        Rating rating = new Rating(
                product.orElse(null),
                ratingCustomer.orElse(null),
                ratedCustomer.orElse(null),
                request.grade(),
                request.comment()

        );
        ratingDAO.insertRating(rating);
    }
}
