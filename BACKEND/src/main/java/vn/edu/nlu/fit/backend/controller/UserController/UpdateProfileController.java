package vn.edu.nlu.fit.backend.controller.UserController;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.backend.dao.UserDAO;
import vn.edu.nlu.fit.backend.model.User;

import java.io.IOException;

@WebServlet(name = "UpdateProfileController", value = "/user/update-profile")
public class UpdateProfileController extends HttpServlet {
    private UserDAO dao = new UserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("userInSession");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String email = request.getParameter("email");
        String fullName = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        int userId = currentUser.getId();

        boolean success = dao.updateProfile(userId, email, fullName, phone, gender, dob);

        if (success) {
            currentUser.setEmail(email);
            currentUser.setFullName(fullName);
            currentUser.setPhone(phone);
            currentUser.setGender(gender);
            currentUser.setDob(dob);
            session.setAttribute("userInSession", currentUser);

            request.setAttribute("message", "Cập nhật thông tin thành công!");
        } else {
            request.setAttribute("error", "Có lỗi xảy ra, vui lòng thử lại.");
        }

        request.getRequestDispatcher("/user/userprofile.jsp").forward(request, response);
    }
}