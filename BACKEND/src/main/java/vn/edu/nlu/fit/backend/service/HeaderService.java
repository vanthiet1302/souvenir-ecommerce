package vn.edu.nlu.fit.backend.service;

import vn.edu.nlu.fit.backend.dao.CategoryDAO;
import vn.edu.nlu.fit.backend.model.Category;

import java.util.List;

public class HeaderService {

    private final CategoryDAO categoryDAO = new CategoryDAO();

    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    public List<Category> getTopCategories(int limit) {
        return categoryDAO.getTopSellingCategories(limit);
    }
}
