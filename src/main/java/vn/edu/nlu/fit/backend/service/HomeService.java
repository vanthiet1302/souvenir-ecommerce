package vn.edu.nlu.fit.backend.service;

import vn.edu.nlu.fit.backend.dao.HomeDAO;
import vn.edu.nlu.fit.backend.dao.ProductDAO;
import vn.edu.nlu.fit.backend.dao.PromotionDAO;
import vn.edu.nlu.fit.backend.dto.HomeCategoryDTO;
import vn.edu.nlu.fit.backend.dto.HomePageDTO;
import vn.edu.nlu.fit.backend.dto.ProductCardDTO;
import vn.edu.nlu.fit.backend.util.ProductCardMapper;
import vn.edu.nlu.fit.backend.model.Product;
import vn.edu.nlu.fit.backend.model.Promotion;

import java.util.ArrayList;
import java.util.List;

public class HomeService {

    private final HomeDAO homeDAO = new HomeDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final PromotionDAO promotionDAO = new PromotionDAO();

    private static final int BANNER_LIMIT = 5;
    private static final int TOP_CATEGORY_LIMIT = 5;
    private static final int EXTENSION_LIMIT = 6;
    private static final int CATEGORY_PRODUCT_LIMIT = 4;
    private static final int TOP_RATED_LIMIT = 4;
    private static final int NEWEST_LIMIT = 4;

    public HomePageDTO getHomePageData() {

        HomePageDTO dto = new HomePageDTO();

        /* ================= 1. BANNER ================= */
        dto.setBannerCategories(
                HomeCategoryDTO.fromCategories(
                        homeDAO.getBannerCategories(BANNER_LIMIT)
                )
        );

        /* ================= 2. TOP CATEGORY ================= */
        dto.setTopCategorySections(
                HomeCategoryDTO.fromCategories(
                        homeDAO.getTopCategoriesWithProducts(
                                TOP_CATEGORY_LIMIT,
                                CATEGORY_PRODUCT_LIMIT
                        )
                )
        );

        /* ================= 3. EXTENSION CATEGORY ================= */
        dto.setExtensionSections(
                HomeCategoryDTO.fromCategories(
                        homeDAO.getExtensionCategories(EXTENSION_LIMIT)
                )
        );

        /* ================= 4. TOP RATED PRODUCTS ================= */
        dto.setTopRatedProductCards(
                mapToProductCardDTOs(
                        productDAO.getTopRatedProducts(TOP_RATED_LIMIT)
                )
        );

        /* ================= 5. NEWEST PRODUCTS ================= */
        dto.setNewestProductCards(
                mapToProductCardDTOs(
                        productDAO.getNewestProducts(NEWEST_LIMIT)
                )
        );

        /* ================= 6. MAP PRODUCT CHO CATEGORY ================= */
        dto.getTopCategorySections().forEach(section ->
                section.setProductCards(
                        mapToProductCardDTOs(
                                section.getCategory().getProducts()
                        )
                )
        );

        return dto;
    }

    /* ================= HELPER ================= */
    private List<ProductCardDTO> mapToProductCardDTOs(List<Product> products) {

        List<ProductCardDTO> cards = new ArrayList<>();
        if (products == null || products.isEmpty()) return cards;

        for (Product p : products) {
            Promotion promo =
                    promotionDAO.getActivePromotionByProductId(p.getId());

            cards.add(ProductCardMapper.from(p, promo));
        }
        return cards;
    }
}
