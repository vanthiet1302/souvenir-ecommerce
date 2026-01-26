package vn.edu.nlu.fit.backend.controller;

import vn.edu.nlu.fit.backend.dto.ProductDetailDTO;
import vn.edu.nlu.fit.backend.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

        /* ===== 1. VALIDATE PRODUCT ID ===== */
        int productId;
        try {
            productId = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        /* ===== 2. LOAD DATA ===== */
        ProductDetailDTO dto = productService.getProductDetail(productId);
        if (dto == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        /* ===== 3. HEADER MODE (BREADCRUMB) ===== */
        request.setAttribute("headerMode", "BREADCRUMB");
        request.setAttribute("breadcrumbCategory", dto.getCategory());
        request.setAttribute("breadcrumbProduct", dto.getProduct());

        /* ===== 4. PAGE DATA ===== */
        request.setAttribute("data", dto);

        /* ===== 5. LAYOUT CONFIG ===== */
        request.setAttribute("pageTitle", dto.getProduct().getName());
        request.setAttribute("contentPage", "product.jsp");

        // CSS & JS riêng cho Product Detail
        request.setAttribute("pageCss", "ProductDetail.css");
        request.setAttribute("pageJs", "ProductDetail.js");

        /* ===== 6. FORWARD TO MAIN LAYOUT ===== */
        request.getRequestDispatcher("layoutMain.jsp")
                .forward(request, response);
    }
}
