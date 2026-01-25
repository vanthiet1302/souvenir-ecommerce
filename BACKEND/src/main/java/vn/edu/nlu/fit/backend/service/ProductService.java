package vn.edu.nlu.fit.backend.service;

import vn.edu.nlu.fit.backend.dao.*;
import vn.edu.nlu.fit.backend.dto.ProductDetailDTO;
import vn.edu.nlu.fit.backend.model.*;

import java.util.Map;

public class ProductService {

    private final ProductDAO productDAO = new ProductDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final PromotionDAO promotionDAO = new PromotionDAO();
    private final ProductSpecificationDAO specificationDAO = new ProductSpecificationDAO();
    private final ReviewDAO reviewDAO = new ReviewDAO();

    public ProductDetailDTO getProductDetail(int productId) {

        Product product = productDAO.getProductById(productId);
        if (product == null) return null;

        Category category = categoryDAO.getCategoryById(product.getCategoryId());
        Promotion promotion = promotionDAO.getActivePromotionByProductId(productId);

        ReviewSummary summary = reviewDAO.getReviewSummaryByProductId(productId);

        Map<Integer, Integer> ratingCount = reviewDAO.countReviewsByRating(productId);
        for (int i = 1; i <= 5; i++) ratingCount.putIfAbsent(i, 0);

        ProductDetailDTO dto = new ProductDetailDTO();
        dto.setProduct(product);
        dto.setCategory(category);
        dto.setPromotion(promotion);
        dto.setSpecifications(specificationDAO.getByProductId(productId));
        if (summary != null) {
            dto.setAvgRating(summary.getAvgRating());
            dto.setTotalReviews(summary.getTotalReviews());
        } else {
            dto.setAvgRating(0);
            dto.setTotalReviews(0);
        }
        dto.setAvgRating(summary.getAvgRating());
        dto.setTotalReviews(summary.getTotalReviews());
        dto.setRatingCount(ratingCount);

        dto.setRelatedProducts(
                productDAO.getRelatedProducts(
                        product.getCategoryId(),
                        productId,
                        6
                )
        );

        if (promotion != null) {
            double discounted =
                    product.getPrice() * (100 - promotion.getDiscountPercent()) / 100.0;
            dto.setDiscountedPrice(discounted);
        }

        return dto;
    }
}
