package vn.edu.nlu.fit.backend.dao;

import vn.edu.nlu.fit.backend.model.Category;
import vn.edu.nlu.fit.backend.model.Product;

import java.util.ArrayList;
import java.util.List;

public class HomeDAO {

    private final CategoryDAO categoryDAO;
    private final ProductDAO productDAO;

    public HomeDAO() {
        this.categoryDAO = new CategoryDAO();
        this.productDAO = new ProductDAO();
    }

    /**
     * ================= HOME – SECTION CHÍNH =================
     * - 5 category bán chạy nhất
     * - Mỗi category có 8 sản phẩm bán chạy
     */
    public List<Category> getTopCategoriesForHome() {
        List<Category> categories = categoryDAO.getTopSellingCategories(5);

        for (Category category : categories) {
            List<Product> products =
                    productDAO.getTopSellingByCategory(category.getId(), 8);
            category.setProducts(products);
        }
        return categories;
    }

    /**
     * ================= HOME – EXTENSION SECTION =================
     * - Các category không nằm trong top bán chạy
     * - + Sản phẩm đánh giá tốt
     * - + Sản phẩm mới
     */
    public List<Category> getExtensionSections() {
        List<Category> result = new ArrayList<>();

        // ID của top 5 category
        List<Integer> topCategoryIds =
                categoryDAO.getTopSellingCategoryIds(5);

        // 1. Category còn lại
        List<Category> remainCategories =
                categoryDAO.getCategoriesNotIn(topCategoryIds);

        for (Category category : remainCategories) {
            List<Product> products =
                    productDAO.getTopSellingByCategory(category.getId(), 8);
            category.setProducts(products);
            result.add(category);
        }

        // 2. Category logic – Đánh giá tốt
        Category topRated = new Category();
        topRated.setId(-1);
        topRated.setName("SẢN PHẨM ĐÁNH GIÁ TỐT");
        topRated.setProducts(productDAO.getTopRatedProducts(8));
        result.add(topRated);

        // 3. Category logic – Sản phẩm mới
        Category newest = new Category();
        newest.setId(-2);
        newest.setName("SẢN PHẨM MỚI");
        newest.setProducts(productDAO.getNewestProducts(8));
        result.add(newest);

        return result;
    }
}
