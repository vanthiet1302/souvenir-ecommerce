package vn.edu.nlu.fit.backend.controller;

import vn.edu.nlu.fit.backend.dao.CategoryDAO;
import vn.edu.nlu.fit.backend.dao.ProductDAO;
import vn.edu.nlu.fit.backend.model.Category;
import vn.edu.nlu.fit.backend.model.Product;
import vn.edu.nlu.fit.backend.Enums.ProductSort;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/category")
public class ProductTypeController extends HttpServlet {

    private static final int PAGE_SIZE = 12;

    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int categoryId;
        try {
            categoryId = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        Integer minPrice = parseInteger(request.getParameter("minPrice"));
        Integer maxPrice = parseInteger(request.getParameter("maxPrice"));
        ProductSort sort = parseSort(request.getParameter("sort"));

        int page = parseInteger(request.getParameter("page"), 1);
        int offset = (page - 1) * PAGE_SIZE;

        List<Product> products = productDAO.getProductsByCategoryWithFilter(
                categoryId, minPrice, maxPrice, sort, offset, PAGE_SIZE
        );

        int totalProducts = productDAO.countProductsByCategoryWithFilter(
                categoryId, minPrice, maxPrice
        );

        int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

        Category category = categoryDAO.getCategoryById(categoryId);
        List<Product> randomRelated = productDAO.getRandomRelated(8);

        /* ===== HEADER DATA ===== */
        request.setAttribute("page", "PRODUCT_TYPE");
        request.setAttribute("breadcrumbCategory", category);

        /* ===== PAGE DATA ===== */
        request.setAttribute("category", category);
        request.setAttribute("products", products);
        request.setAttribute("randomRelated", randomRelated);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("sort", sort);
        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);

        request.getRequestDispatcher("/WEB-INF/views/productType.jsp")
                .forward(request, response);
    }

    private Integer parseInteger(String value) {
        try {
            return value != null ? Integer.parseInt(value) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private int parseInteger(String value, int defaultValue) {
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private ProductSort parseSort(String sort) {
        if (sort == null) return ProductSort.POPULAR;

        return switch (sort) {
            case "price_asc" -> ProductSort.PRICE_ASC;
            case "price_desc" -> ProductSort.PRICE_DESC;
            case "newest" -> ProductSort.NEWEST;
            default -> ProductSort.POPULAR;
        };
    }
}
