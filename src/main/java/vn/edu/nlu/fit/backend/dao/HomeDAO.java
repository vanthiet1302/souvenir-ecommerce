package vn.edu.nlu.fit.backend.dao;

import vn.edu.nlu.fit.backend.model.Category;
import vn.edu.nlu.fit.backend.model.Product;

import java.util.List;

public class HomeDAO {

    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final ProductDAO productDAO = new ProductDAO();

    /* ========= 1. BANNER ========= */
    /* ========= 1. BANNER = TOP SELLING ========= */
    public List<Category> getBannerCategories(int limit) {
        return categoryDAO.getTopSellingCategories(limit);
    }

    /* ========= 2. TOP CATEGORY + PRODUCT ========= */
    public List<Category> getTopCategoriesWithProducts(int categoryLimit, int productLimit) {

        List<Category> categories =
                categoryDAO.getTopSellingCategories(categoryLimit);

        for (Category category : categories) {
            List<Product> products =
                    productDAO.getTopSellingByCategory(category.getId(), productLimit);

            category.setProducts(products);
        }
        return categories;
    }


    /* ========= 3. EXTENSION = CATEGORY CÒN LẠI ========= */
    public List<Category> getExtensionCategories(int limit) {
        List<Integer> topIds = categoryDAO.getTopSellingCategoryIds(5);
        List<Category> remain = categoryDAO.getCategoriesNotIn(topIds);
        return remain.subList(0, Math.min(limit, remain.size()));
    }

}
