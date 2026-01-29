package vn.edu.nlu.fit.backend.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.backend.dao.UserDAO;
import vn.edu.nlu.fit.backend.model.User;

import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {

    private final UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String loginDetail = request.getParameter("loginDetail");
        String password = request.getParameter("password");

        if (loginDetail == null || loginDetail.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập Email hoặc Số điện thoại!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        User authUser = dao.login(loginDetail, password);

        if (authUser != null) {
            HttpSession session = request.getSession(true);

            // userInSession
            session.setAttribute("userInSession", authUser);


            if ("Admin".equals(authUser.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
            }

        } else {
            request.setAttribute("error", "Tài khoản hoặc mật khẩu không chính xác!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
