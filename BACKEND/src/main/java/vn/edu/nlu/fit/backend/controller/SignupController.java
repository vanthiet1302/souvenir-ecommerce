package vn.edu.nlu.fit.backend.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.backend.dao.UserDAO;
import java.io.IOException;

@WebServlet(name = "SignupController", value = "/signup")
public class SignupController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fullName = request.getParameter("full_name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        // KIỂM TRA ĐỊNH DẠNG (VALIDATION)
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (email == null || !email.matches(emailRegex)) {
            request.setAttribute("error", "Invalid email format!");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        // Kiểm tra số điện thoại ()
        if (phone == null || !phone.matches("\\d{10}")) {
            request.setAttribute("error", "Phone number must be exactly 10 digits!");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        if (password == null || password.length() < 8) {
            request.setAttribute("error", "Password must be at least 8 characters!");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Confirmation password does not match!");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        // GỌI DAO ĐỂ LƯU VÀO CƠ SỞ DỮ LIỆU
        UserDAO dao = new UserDAO();
        // Truyền thêm tham số phone vào hàm register
        boolean isRegistered = dao.register(email, password, fullName, phone);

        if (isRegistered) {
            request.setAttribute("success", "Registration successful! You can now log in.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Registration failed! Email might already be in use.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }
}