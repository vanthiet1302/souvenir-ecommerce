package vn.edu.nlu.fit.backend.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.backend.cart.Cart;

import java.io.IOException;

@WebServlet(name = "RemoveCartController", value = "/cart/remove")
public class RemoveCartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            cart.removeItem(productId);
            session.setAttribute("cart", cart);
        } catch (NumberFormatException ignored) {
        }

        // Quay lại trang giỏ hàng
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
