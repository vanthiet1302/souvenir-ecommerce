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

        // 1. category id
        int categoryId;
        try {
            categoryId = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.sendRedirect("home");
            return;
        }

        // 2. filter params
        Integer minPrice = parseInt(request.getParameter("minPrice"));
        Integer maxPrice = parseInt(request.getParameter("maxPrice"));

        ProductSort sort = parseSort(request.getParameter("sort"));

        int page = parseInt(request.getParameter("page"), 1);
        int pageSize = 12;
        int offset = (page - 1) * pageSize;
        // random related products
        List<Product> randomRelated = productDAO.getRandomRelated(8);
        request.setAttribute("randomRelated", randomRelated);


        // 3. DB
        List<Product> products = productDAO.getProductsByCategoryWithFilter(
                categoryId, minPrice, maxPrice, sort, offset, pageSize
        );

        int totalProducts = productDAO.countProductsByCategoryWithFilter(
                categoryId, minPrice, maxPrice
        );

        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        // 4. category info
        Category category = categoryDAO.getCategoryById(categoryId);

        // 5. send to JSP
        request.setAttribute("category", category);
        request.setAttribute("products", products);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("sort", sort);
        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);

        request.getRequestDispatcher("/WEB-INF/views/productType.jsp")
                .forward(request, response);
    }

    private Integer parseInt(String value) {
        try {
            return value != null ? Integer.parseInt(value) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private int parseInt(String value, int defaultValue) {
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


