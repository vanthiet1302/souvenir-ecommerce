package vn.edu.nlu.fit.backend.controller;

import vn.edu.nlu.fit.backend.dto.ProductDetailDTO;
import vn.edu.nlu.fit.backend.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/product")
public class ProductDetailController extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() {
        productService = new ProductService();
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

        ProductDetailDTO dto = productService.getProductDetail(productId);
        if (dto == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        /* ===== HEADER MODE ===== */
        request.setAttribute("headerMode", "BREADCRUMB");
        request.setAttribute("breadcrumbCategory", dto.getCategory());
        request.setAttribute("breadcrumbProduct", dto.getProduct());

        /* ===== PAGE DATA ===== */
        request.setAttribute("data", dto);

        request.getRequestDispatcher("product.jsp").forward(request, response);
    }
}
