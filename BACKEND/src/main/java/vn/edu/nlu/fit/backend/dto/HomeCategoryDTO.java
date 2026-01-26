package vn.edu.nlu.fit.backend.dto;

import vn.edu.nlu.fit.backend.model.Category;
import vn.edu.nlu.fit.backend.model.Product;

import java.util.List;

public class HomeCategoryDTO {

    private Category category;
    private List<Product> products;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
