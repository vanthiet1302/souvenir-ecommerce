package vn.edu.nlu.fit.backend.controller.UserController;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.backend.model.User;
import java.io.IOException;

@WebServlet(name = "OrderController", value = "/user/order")
public class OrderController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userInSession");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Logic lấy orderList từ DAO sẽ đặt ở đây
        request.getRequestDispatcher("/user/userorder.jsp").forward(request, response);
    }
}