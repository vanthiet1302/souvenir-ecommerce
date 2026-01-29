package vn.edu.nlu.fit.backend.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ShoppingCartController", value = "/shoppingcart")
public class ShoppingCartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Servlet này chỉ làm nhiệm vụ hiển thị trang giỏ hàng
        request.getRequestDispatcher("/shoppingcart.jsp").forward(request, response);
    }
}