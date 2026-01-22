package vn.edu.nlu.fit.backend.controller.UserController;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.backend.model.User;
import java.io.IOException;

@WebServlet(name = "FavouriteController", value = "/user/favourite")
public class FavouriteController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("userInSession") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/user/userfavourite.jsp").forward(request, response);
    }
}