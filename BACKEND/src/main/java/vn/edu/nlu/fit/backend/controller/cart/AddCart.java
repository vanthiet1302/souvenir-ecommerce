package vn.edu.nlu.fit.backend.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.nlu.fit.backend.cart.Cart;
import vn.edu.nlu.fit.backend.dao.ProductDAO;
import vn.edu.nlu.fit.backend.model.Product;
import vn.edu.nlu.fit.backend.model.User;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AddCart", value = "/cart/add")
public class AddCart extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("userInSession");

        // ===== CHƯA LOGIN =====
        if (user == null) {

            String ajaxHeader = request.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equals(ajaxHeader)) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.print("""
                        {
                          "success": false,
                          "requireLogin": true,
                          "message": "Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng"
                        }
                        """);
                out.flush();
                return;
            }

            session.setAttribute("redirectAfterLogin", request.getHeader("Referer"));
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ===== LẤY DỮ LIỆU =====
        int productId;
        int quantity;

        try {
            productId = Integer.parseInt(request.getParameter("productId"));
            quantity = Integer.parseInt(request.getParameter("quantity"));
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        Product product = new ProductDAO().getProductById(productId);
        if (product == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // ===== CART =====
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

        cart.addItem(product, quantity);
        session.setAttribute("cart", cart);

        // ===== AJAX =====
        String ajaxHeader = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(ajaxHeader)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print("""
                    {
                      "success": true,
                      "message": "Đã thêm vào giỏ hàng",
                      "cartCount": %d
                    }
                    """.formatted(cart.totalQuantity()));
            out.flush();
            return;
        }

        // ===== FORM THƯỜNG =====
        response.sendRedirect(request.getHeader("Referer"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/home");
    }
}
