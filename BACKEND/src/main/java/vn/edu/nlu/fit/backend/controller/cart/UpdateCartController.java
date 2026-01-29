package vn.edu.nlu.fit.backend.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.nlu.fit.backend.cart.Cart;

import java.io.IOException;

@WebServlet("/cart/update")
public class UpdateCartController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");

            if (cart == null) {
                response.getWriter().write("{\"success\": false}");
                return;
            }

            if (quantity <= 0) {
                cart.removeItem(productId);
            } else {
                cart.updateItem(productId, quantity);
            }

            session.setAttribute("cart", cart);

            response.getWriter().write("""
                {
                  "success": true,
                  "totalQuantity": %d,
                  "total": %f
                }
                """.formatted(cart.totalQuantity(), cart.total()));

        } catch (Exception e) {
            response.getWriter().write("{\"success\": false}");
        }
    }
}
