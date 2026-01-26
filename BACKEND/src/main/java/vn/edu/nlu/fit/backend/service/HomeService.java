package vn.edu.nlu.fit.backend.service;

import vn.edu.nlu.fit.backend.dao.CategoryDAO;
import vn.edu.nlu.fit.backend.dao.ProductDAO;
import vn.edu.nlu.fit.backend.dto.HomeCategoryDTO;
import vn.edu.nlu.fit.backend.dto.HomePageDTO;
import vn.edu.nlu.fit.backend.model.Category;
import vn.edu.nlu.fit.backend.model.Product;

import java.util.ArrayList;
import java.util.List;

public class HomeService {

    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final ProductDAO productDAO = new ProductDAO();

    private static final int PRODUCT_LIMIT = 6;

    public HomePageDTO getHomePageData() {

        HomePageDTO dto = new HomePageDTO();

        /* ========= 1. BANNER (CATEGORY IMAGE) ========= */
        List<Category> allCategories = categoryDAO.getAllCategories();
        List<HomeCategoryDTO> bannerList = new ArrayList<>();

        for (Category c : allCategories) {
            HomeCategoryDTO item = new HomeCategoryDTO();
            item.setCategory(c);
            bannerList.add(item);
        }

        dto.setBannerCategories(bannerList);

        /* ========= 2. TOP SELLING CATEGORIES ========= */
        List<Category> topCategories =
                categoryDAO.getTopSellingCategories(5);

        List<HomeCategoryDTO> topSections = new ArrayList<>();

        for (Category c : topCategories) {
            List<Product> products =
                    productDAO.getTopSellingByCategory(c.getId(), PRODUCT_LIMIT);

            HomeCategoryDTO section = new HomeCategoryDTO();
            section.setCategory(c);
            section.setProducts(products);
            topSections.add(section);
        }

        dto.setTopCategorySections(topSections);

        /* ========= 3. EXTENSION CATEGORIES ========= */
        List<Integer> usedIds =
                categoryDAO.getTopSellingCategoryIds(5);

        List<Category> extraCategories =
                categoryDAO.getCategoriesNotIn(usedIds);

        List<HomeCategoryDTO> extensionSections = new ArrayList<>();

        for (Category c : extraCategories) {
            HomeCategoryDTO section = new HomeCategoryDTO();
            section.setCategory(c);
            extensionSections.add(section);
        }

        dto.setExtensionSections(extensionSections);

        return dto;
    }
}
