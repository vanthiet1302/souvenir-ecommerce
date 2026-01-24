package vn.edu.nlu.fit.backend.service;

import vn.edu.nlu.fit.backend.dao.CategoryDAO;
import vn.edu.nlu.fit.backend.dao.ProductDAO;
import vn.edu.nlu.fit.backend.dto.ProductTypeDTO;
import vn.edu.nlu.fit.backend.Enums.ProductSort;
import vn.edu.nlu.fit.backend.model.Category;
import vn.edu.nlu.fit.backend.model.Product;

import java.util.List;

public class ProductTypeService {

    private static final int PAGE_SIZE = 12;

    private final ProductDAO productDAO = new ProductDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    public ProductTypeDTO getProductType(
            int categoryId,
            Integer minPrice,
            Integer maxPrice,
            ProductSort sort,
            int page
    ) {
        Category category = categoryDAO.getCategoryById(categoryId);
        if (category == null) return null;

        int offset = (page - 1) * PAGE_SIZE;

        List<Product> products =
                productDAO.getProductsByCategoryWithFilter(
                        categoryId, minPrice, maxPrice, sort, offset, PAGE_SIZE
                );

        int totalProducts =
                productDAO.countProductsByCategoryWithFilter(
                        categoryId, minPrice, maxPrice
                );

        int totalPages =
                (int) Math.ceil((double) totalProducts / PAGE_SIZE);

        ProductTypeDTO dto = new ProductTypeDTO();
        dto.setCategory(category);
        dto.setProducts(products);
        dto.setCurrentPage(page);
        dto.setTotalPages(totalPages);
        dto.setTotalProducts(totalProducts);
        dto.setMinPrice(minPrice);
        dto.setMaxPrice(maxPrice);
        dto.setSort(sort);


        dto.setSortParam(
                sort != null ? sort.name().toLowerCase() : "popular"
        );

        return dto;
    }
}
