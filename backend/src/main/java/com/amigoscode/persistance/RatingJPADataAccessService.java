package com.amigoscode.persistance;

import com.amigoscode.model.Rating;
import com.amigoscode.persistance.interfaces.RatingDAO;
import com.amigoscode.persistance.interfaces.repository.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.amigoscode.constants.Constants.PAGE_SIZE;

@Repository("jpaRating")
public class RatingJPADataAccessService implements RatingDAO {

    private static final Logger log = LoggerFactory.getLogger(RatingJPADataAccessService.class);
    private final RatingRepository ratingRepository;

    public RatingJPADataAccessService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> selectAllRatings(int productId, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("dateAndTime").descending());
        Page<Rating> page = ratingRepository.findByProductIdOrderByDateAndTimeDesc(productId, pageable);
        log.info("selectAllRatings for productId {}: {}", productId, page.getContent());
        return page.getContent();
    }

    @Override
    public void insertRating(Rating rating) {
        log.info("Save rating " + rating.toString());
        ratingRepository.save(rating);
    }

}
