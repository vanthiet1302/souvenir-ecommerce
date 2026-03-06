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
            dto.setImage(p.getImage());
        } else {
            dto.setImage("/assets/images/products/default.png");
        }

        /* ================= BASE INFO ================= */
        dto.setTotalSold(p.getTotalSold());
        dto.setAvgRating(p.getAvgRating());
        dto.setReviewCount(p.getReviewCount());

        double originalPrice = p.getPrice();

        /* ================= PROMOTION ================= */
        if (promo != null && promo.getDiscountPercent() > 0) {

            int percent = promo.getDiscountPercent();
            double salePrice = originalPrice * (100 - percent) / 100.0;

            dto.setDiscountPercent(percent);
            dto.setOriginalPrice(originalPrice);
            dto.setDiscountedPrice(salePrice);

            // giá hiển thị chính
            dto.setPrice(salePrice);

        } else {
            // Không có khuyến mãi
            dto.setDiscountPercent(null);
            dto.setDiscountedPrice(null);
            dto.setOriginalPrice(originalPrice);

            dto.setPrice(originalPrice);
        }

        return dto;
    }
}
