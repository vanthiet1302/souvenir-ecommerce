package vn.edu.nlu.fit.backend.controller;

import vn.edu.nlu.fit.backend.dao.CategoryDAO;
import vn.edu.nlu.fit.backend.dao.ProductDAO;
import vn.edu.nlu.fit.backend.model.Category;
import vn.edu.nlu.fit.backend.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/product")
public class ProductDetailController extends HttpServlet {

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

        int productId;
        try {
            productId = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        Product product = productDAO.getProductById(productId);
        if (product == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        Category category = categoryDAO.getCategoryById(product.getCategoryId());

        /* ===== HEADER DATA ===== */
        request.setAttribute("page", "PRODUCT_DETAIL");
        request.setAttribute("breadcrumbCategory", category);
        request.setAttribute("breadcrumbProduct", product);

        /* ===== PAGE DATA ===== */
        request.setAttribute("product", product);

        request.getRequestDispatcher("/WEB-INF/views/productDetail.jsp")
                .forward(request, response);
    }
}
