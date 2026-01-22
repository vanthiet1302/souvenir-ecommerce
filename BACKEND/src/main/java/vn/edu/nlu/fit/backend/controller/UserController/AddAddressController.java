package vn.edu.nlu.fit.backend.controller.UserController;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.backend.dao.UserDAO;
import vn.edu.nlu.fit.backend.model.User;

import java.io.IOException;
@WebServlet(name = "AddAddressController", value = "/user/add-address")
public class AddAddressController extends HttpServlet {
    private UserDAO dao = new UserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userInSession");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String detail = request.getParameter("addressDetail");
        String city = request.getParameter("city");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");

        boolean success = dao.addAddress(user.getId(), detail, city, district, ward);

        if (success) {
            request.setAttribute("message", "Thêm địa chỉ mới thành công!");
        } else {
            request.setAttribute("error", "Không thể thêm địa chỉ, vui lòng thử lại.");
        }

        response.sendRedirect(request.getContextPath() + "/user/userprofile.jsp");
    }
}
