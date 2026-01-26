package vn.edu.nlu.fit.backend.util;

import vn.edu.nlu.fit.backend.dto.ProductCardDTO;
import vn.edu.nlu.fit.backend.model.Product;
import vn.edu.nlu.fit.backend.model.Promotion;

public class ProductCardMapper {

    public static ProductCardDTO from(Product p, Promotion promo) {

        ProductCardDTO dto = new ProductCardDTO();

        dto.setId(p.getId());
        dto.setName(p.getName());

        /* ================= IMAGE ================= */
        if (p.getImage() != null && !p.getImage().isBlank()) {
            dto.setImage(  p.getImage());
        } else {
            dto.setImage("/assets/images/products/default.png");
        }

        /* ================= BASE INFO ================= */
        dto.setPrice(p.getPrice());
        dto.setTotalSold(p.getTotalSold());
        dto.setAvgRating(p.getAvgRating());
        dto.setReviewCount(p.getReviewCount());

        /* ================= PROMOTION ================= */
        if (promo != null) {
            dto.setDiscountPercent(promo.getDiscountPercent());

            double discounted =
                    p.getPrice() * (100 - promo.getDiscountPercent()) / 100.0;

            dto.setDiscountedPrice(discounted);
            dto.setOriginalPrice(p.getPrice());
        } else {
            dto.setDiscountPercent(null);
            dto.setDiscountedPrice(null);
            dto.setOriginalPrice(null);
        }

        return dto;
    }
}
