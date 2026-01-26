package vn.edu.nlu.fit.backend.service;

import vn.edu.nlu.fit.backend.dao.*;
import vn.edu.nlu.fit.backend.dto.ProductCardDTO;
import vn.edu.nlu.fit.backend.dto.ProductDetailDTO;
import vn.edu.nlu.fit.backend.model.*;
import vn.edu.nlu.fit.backend.util.ProductCardMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductService {

    private final ProductDAO productDAO = new ProductDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final PromotionDAO promotionDAO = new PromotionDAO();
    private final ProductSpecificationDAO specificationDAO = new ProductSpecificationDAO();
    private final ReviewDAO reviewDAO = new ReviewDAO();

    public ProductDetailDTO getProductDetail(int productId) {

        /* ================= PRODUCT ================= */
        Product product = productDAO.getProductById(productId);
        if (product == null) return null;

        Category category = categoryDAO.getCategoryById(product.getCategoryId());
        Promotion promotion = promotionDAO.getActivePromotionByProductId(productId);

        /* ================= REVIEWS ================= */
        ReviewSummary summary = reviewDAO.getReviewSummaryByProductId(productId);

        Map<Integer, Integer> ratingCount = reviewDAO.countReviewsByRating(productId);
        for (int i = 1; i <= 5; i++) {
            ratingCount.putIfAbsent(i, 0);
        }
        /* ================= RELATED PRODUCTS ================= */
        List<Product> relatedProducts =
                productDAO.getRelatedProducts(
                        product.getCategoryId(),
                        productId,
                        5
                );

        List<ProductCardDTO> relatedCards = new ArrayList<>();

        if (relatedProducts != null && !relatedProducts.isEmpty()) {
            for (Product rp : relatedProducts) {
                Promotion promo =
                        promotionDAO.getActivePromotionByProductId(rp.getId());

                relatedCards.add(
                        ProductCardMapper.from(rp, promo)
                );
            }
        }

        /* ================= DTO ================= */
        ProductDetailDTO dto = new ProductDetailDTO();
        dto.setProduct(product);
        dto.setCategory(category);
        dto.setPromotion(promotion);
        dto.setSpecifications(specificationDAO.getByProductId(productId));

        // ===== FIX LOGIC: KHÔNG SET TRÙNG, KHÔNG NPE =====
        if (summary != null) {
            dto.setAvgRating(summary.getAvgRating());
            dto.setTotalReviews(summary.getTotalReviews());
        } else {
            dto.setAvgRating(0);
            dto.setTotalReviews(0);
        }

        dto.setRatingCount(ratingCount);



        dto.setRelatedProductCards(relatedCards);

        /* ================= PRICE ================= */
        if (promotion != null) {
            double discounted =
                    product.getPrice() * (100 - promotion.getDiscountPercent()) / 100.0;
            dto.setDiscountedPrice(discounted);
        }

        return dto;
    }
}
