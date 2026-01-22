package vn.edu.nlu.fit.backend.controller.UserController;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.backend.dao.UserDAO;
import vn.edu.nlu.fit.backend.model.User;
import java.io.IOException;

@WebServlet(name = "ProfileController", value = "/user/profile")
public class ProfileController extends HttpServlet {
    private UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userInSession");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("listAddr", dao.getAddressesByUserId(user.getId()));
        request.getRequestDispatcher("/user/userprofile.jsp").forward(request, response);
    }
}