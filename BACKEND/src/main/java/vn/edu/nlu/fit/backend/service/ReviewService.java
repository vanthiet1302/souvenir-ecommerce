package vn.edu.nlu.fit.backend.service;

import vn.edu.nlu.fit.backend.dao.ReviewDAO;
import vn.edu.nlu.fit.backend.model.Review;

import java.util.List;

public class ReviewService {

    private final ReviewDAO reviewDAO = new ReviewDAO();

    public List<Review> getReviews(
            int productId,
            Integer rating,
            String sort,
            int offset,
            int limit
    ) {
        return reviewDAO.getReviewsByProductWithFilter(
                productId, rating, sort, offset, limit
        );
    }
}
