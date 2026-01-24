package vn.edu.nlu.fit.backend.dto;

import vn.edu.nlu.fit.backend.model.Category;
import vn.edu.nlu.fit.backend.model.Product;
import vn.edu.nlu.fit.backend.Enums.ProductSort;

import java.util.List;

public class ProductTypeDTO {

    private Category category;
    private List<Product> products;

    private int currentPage;
    private int totalPages;
    private int totalProducts;

    private Integer minPrice;
    private Integer maxPrice;

    private ProductSort sort;
    private String sortParam;

    /* ===== Getter / Setter ===== */

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public int getTotalProducts() { return totalProducts; }
    public void setTotalProducts(int totalProducts) { this.totalProducts = totalProducts; }

    public Integer getMinPrice() { return minPrice; }
    public void setMinPrice(Integer minPrice) { this.minPrice = minPrice; }

    public Integer getMaxPrice() { return maxPrice; }
    public void setMaxPrice(Integer maxPrice) { this.maxPrice = maxPrice; }

    public ProductSort getSort() { return sort; }
    public void setSort(ProductSort sort) { this.sort = sort; }

    public String getSortParam() { return sortParam; }
    public void setSortParam(String sortParam) { this.sortParam = sortParam; }
}
