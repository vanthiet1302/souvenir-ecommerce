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

        // Xac thuc ID
        String idParam = request.getParameter("id");
        if(idParam == null || idParam.trim().isEmpty()){
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        int productId;
        try {
            productId = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        if(productId <= 0){
            response.sendRedirect(request.getContextPath() +"/home");
        }

        // Load data
        ProductDetailDTO dto = productService.getProductDetail(productId);
        if (dto == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // Header mode (BreadCrumb)
        request.setAttribute("headerMode", "BREADCRUMB");
        request.setAttribute("breadcrumbCategory", dto.getCategory());
        request.setAttribute("breadcrumbProduct", dto.getProduct());
        request.setAttribute("enableHeaderOverlay", true);

        // Page Data
        request.setAttribute("data", dto);

        // Layout
        request.setAttribute("pageTitle", dto.getProduct().getName());
        request.setAttribute("contentPage", "product.jsp");

        // Css , Js
        request.setAttribute("pageCss", "ProductDetail.css");
        request.setAttribute("pageJs", "ProductDetail.js");

        // Forward layoutMain
        request.getRequestDispatcher("/layoutMain.jsp").forward(request, response);
    }
}
