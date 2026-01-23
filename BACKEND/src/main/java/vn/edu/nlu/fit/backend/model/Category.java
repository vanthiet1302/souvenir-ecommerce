package vn.edu.nlu.fit.backend.model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private int id;
    private String category_name;
    private String image;
    private List<Product> products;

    public Category() {}

    public Category(int id, String name) {
        this.id = id;
        this.category_name = name;
        this.products = new ArrayList<>();
    }
    public Category(int id, String name, String image) {
        this.id = id;
        this.category_name = name;
        this.image = image;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return category_name;
    }

    public void setName(String name) {
        this.category_name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        products = products;
    }
}

