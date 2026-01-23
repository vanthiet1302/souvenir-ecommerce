package vn.edu.nlu.fit.backend.service;

import vn.edu.nlu.fit.backend.dao.*;
import vn.edu.nlu.fit.backend.dto.ProductDetailDTO;
import vn.edu.nlu.fit.backend.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailService {

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
        List<ProductSpecification> specs = specificationDAO.getByProductId(productId);

        ReviewSummary summary = reviewDAO.getReviewSummaryByProductId(productId);
        Map<Integer, Integer> ratingCount = reviewDAO.countReviewsByRating(productId);

        for (int i = 1; i <= 5; i++) ratingCount.putIfAbsent(i, 0);

        Map<Integer, Integer> ratingPercent = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            int percent = summary.getTotalReviews() == 0
                    ? 0
                    : ratingCount.get(i) * 100 / summary.getTotalReviews();
            ratingPercent.put(i, percent);
        }

        List<Product> related =
                productDAO.getRelatedProducts(
                        product.getCategoryId(),
                        productId,
                        6
                );

        ProductDetailDTO dto = new ProductDetailDTO();
        dto.setProduct(product);
        dto.setCategory(category);
        dto.setPromotion(promotion);
        dto.setSpecifications(specs);
        dto.setRelatedProducts(related);
        dto.setAvgRating(summary.getAvgRating());
        dto.setTotalReviews(summary.getTotalReviews());
        dto.setRatingCount(ratingCount);
        dto.setRatingPercent(ratingPercent);

        return dto;
    }
}
