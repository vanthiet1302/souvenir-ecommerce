package vn.edu.nlu.fit.backend.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.backend.dao.UserDAO;
import vn.edu.nlu.fit.backend.model.User;

import java.io.IOException;

@WebServlet(name = "ProfileController", value = "/user/profile")
public class ProfileController extends HttpServlet {

    private final UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("userInSession") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // FIX: luôn load lại danh sách địa chỉ
        request.setAttribute("listAddr", dao.getAddressesByUserId(user.getId()));
        request.getRequestDispatcher("/user/userprofile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("userInSession") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/user/profile");
            return;
        }

        switch (action) {

            case "update_profile" -> {
                String fullName = request.getParameter("fullName");
                String phone = request.getParameter("phone");
                String gender = request.getParameter("gender");
                String dob = request.getParameter("dob");

                if (dao.updateProfile(user.getId(), fullName, phone, gender, dob)) {
                    user.setFullName(fullName);
                    user.setPhone(phone);
                    user.setGender(gender);
                    user.setDob(dob);
                    session.setAttribute("userInSession", user);
                }
            }

            case "add_address" -> {
                dao.addAddress(
                        user.getId(),
                        request.getParameter("addressDetail"),
                        request.getParameter("city"),
                        request.getParameter("district"),
                        request.getParameter("ward")
                );
            }

            case "update_address" -> {
                int addressId = Integer.parseInt(request.getParameter("addressId"));
                dao.updateAddress(
                        addressId,
                        request.getParameter("detail"),
                        request.getParameter("ward"),
                        request.getParameter("district"),
                        request.getParameter("city")
                );
            }

            case "delete_address" -> {
                int addressId = Integer.parseInt(request.getParameter("addressId"));
                dao.deleteAddress(addressId);
            }

            case "set_default_address" -> {
                int addressId = Integer.parseInt(request.getParameter("addressId"));
                dao.setDefaultAddress(user.getId(), addressId);
            }
        }

        response.sendRedirect(request.getContextPath() + "/user/profile");
    }
}
