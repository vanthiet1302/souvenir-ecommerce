package vn.edu.nlu.fit.backend.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.backend.cart.Cart;

import java.io.IOException;

public class UpdateCartController extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            
            if (cart == null) {
                response.getWriter().write("{\"success\": false, \"message\": \"Giỏ hàng trống\"}");
                return;
            }
            
            if (quantity <= 0) {
                cart.removeItem(productId);
            } else {
                cart.updateItem(productId, quantity);
            }
            
            session.setAttribute("cart", cart);
            
            // Tạo JSON response thủ công
            int totalQty = cart.totalQuantity();
            double total = cart.total();
            String jsonResponse = "{\"success\": true, \"totalQuantity\": " + totalQty + ", \"total\": " + total + "}";
            
            response.getWriter().write(jsonResponse);
            
        } catch (Exception e) {
            response.getWriter().write("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
