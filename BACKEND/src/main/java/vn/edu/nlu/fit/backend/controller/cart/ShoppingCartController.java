package vn.edu.nlu.fit.backend.controller.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ShoppingCartController", value = "/shoppingcart")
public class ShoppingCartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Servlet này chỉ làm nhiệm vụ hiển thị trang giỏ hàng
        request.getRequestDispatcher("/shoppingcart.jsp").forward(request, response);
    }
}