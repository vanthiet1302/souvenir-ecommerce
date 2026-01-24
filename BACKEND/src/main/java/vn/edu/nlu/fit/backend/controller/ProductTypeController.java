package vn.edu.nlu.fit.backend.controller;

import vn.edu.nlu.fit.backend.dto.ProductTypeDTO;
import vn.edu.nlu.fit.backend.Enums.ProductSort;
import vn.edu.nlu.fit.backend.service.ProductTypeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/category")
public class ProductTypeController extends HttpServlet {

    private ProductTypeService productTypeService;

    @Override
    public void init() {
        productTypeService = new ProductTypeService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* ===== 1. VALIDATE CATEGORY ID ===== */
        int categoryId;
        try {
            categoryId = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        /* ===== 2. FILTER PARAMS ===== */
        Integer minPrice = parseInteger(request.getParameter("minPrice"));
        Integer maxPrice = parseInteger(request.getParameter("maxPrice"));
        ProductSort sort = parseSort(request.getParameter("sort"));

        int page = parseInteger(request.getParameter("page"), 1);

        /* ===== 3. SERVICE ===== */
        ProductTypeDTO dto = productTypeService.getProductType(
                categoryId,
                minPrice,
                maxPrice,
                sort,
                page
        );

        if (dto == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        /* ===== 4. HEADER ===== */
        request.setAttribute("headerMode", "BREADCRUMB");
        request.setAttribute("breadcrumbCategory", dto.getCategory());

        /* ===== 5. PAGE DATA ===== */
        request.setAttribute("data", dto);

        request.getRequestDispatcher("/WEB-INF/views/productType.jsp")
                .forward(request, response);
    }

    /* ================= UTIL ================= */

    private Integer parseInteger(String value) {
        try {
            return value != null && !value.isEmpty()
                    ? Integer.parseInt(value)
                    : null;
        } catch (Exception e) {
            return null;
        }
    }

    private int parseInteger(String value, int defaultValue) {
        try {
            return value != null
                    ? Integer.parseInt(value)
                    : defaultValue;
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
