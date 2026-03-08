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

        // Xac thuc ID
        String IdParam = request.getParameter("id");
        if(IdParam == null || IdParam.trim().isEmpty()){
            response.sendRedirect(request.getContextPath()+"/home");
            return;
        }
        int categoryId;
        try {
            categoryId = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        if(categoryId <= 0){
            response.sendRedirect(request.getContextPath()+"/home");
        }

        // Filter param
        Integer minPrice = parseInteger(request.getParameter("minPrice"));
        Integer maxPrice = parseInteger(request.getParameter("maxPrice"));
        ProductSort sort = parseSort(request.getParameter("sort"));

        int page = parseInteger(request.getParameter("page"), 1);
        Integer rating = parseInteger(request.getParameter("rating"));

        // Service
        ProductTypeDTO dto = productTypeService.getProductType(categoryId, minPrice, maxPrice, rating, sort, page);

        if (dto == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // header mode(BreadCrum)
        request.setAttribute("headerMode", "BREADCRUMB");
        request.setAttribute("breadcrumbCategory", dto.getCategory());
        request.setAttribute("enableHeaderOverlay", true);

        // Page data
        request.setAttribute("data", dto);

        // Layout
        request.setAttribute("pageTitle", dto.getCategory().getName());
        request.setAttribute("contentPage", "productType.jsp");
        request.setAttribute("pageCss", "PTypeMain.css");
        request.setAttribute("pageJs", "ProductType.js");

        // Forward layoutMain
        request.getRequestDispatcher("/layoutMain.jsp")
                .forward(request, response);

    }

    // Util (Chuan hoa du lieu)

    private Integer parseInteger(String value) {
        try {
            return value != null && !value.trim().isEmpty() ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private int parseInteger(String value, int defaultValue) {
        try {
            return value != null && !value.trim().isEmpty() ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private ProductSort parseSort(String sort) {
        if (sort == null) return ProductSort.POPULAR;

        return switch (sort.toLowerCase()) {
            case "price_asc" -> ProductSort.PRICE_ASC;
            case "price_desc" -> ProductSort.PRICE_DESC;
            case "newest" -> ProductSort.NEWEST;
            default -> ProductSort.POPULAR;
        };
    }
}
