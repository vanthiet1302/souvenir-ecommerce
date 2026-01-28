package vn.edu.nlu.fit.backend.controller.UserController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.nlu.fit.backend.dao.UserDAO;
import vn.edu.nlu.fit.backend.model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet(name = "ProfileController", value = "/user/profile")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // 1MB
        maxFileSize = 5 * 1024 * 1024,         // 5MB
        maxRequestSize = 10 * 1024 * 1024      // 10MB
)
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

        request.setAttribute("listAddr", dao.getAddressesByUserId(user.getId()));
        request.getRequestDispatcher("/user/userprofile.jsp")
                .forward(request, response);
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

            /* ================= UPDATE PROFILE ================= */
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

            /* ================= CHANGE AVATAR ================= */
            case "change_avatar" -> {
                Part avatarPart = request.getPart("avatarFile");

                if (avatarPart != null && avatarPart.getSize() > 0) {

                    // Lấy tên file gốc
                    String fileName = Paths.get(avatarPart.getSubmittedFileName())
                            .getFileName().toString();

                    // Tạo tên file mới tránh trùng
                    String newFileName = UUID.randomUUID() + "_" + fileName;

                    // Thư mục lưu avatar
                    String uploadDir = request.getServletContext()
                            .getRealPath("/assets/image/Avatar");

                    File dir = new File(uploadDir);
                    if (!dir.exists()) dir.mkdirs();

                    // Lưu file
                    avatarPart.write(uploadDir + File.separator + newFileName);

                    // Update DB
                    dao.updateAvatar(user.getId(), newFileName);

                    // Update session
                    user.setAvatar(newFileName);
                    session.setAttribute("userInSession", user);
                }
            }

            /* ================= ADDRESS ================= */
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
                try {
                    int addressId = Integer.parseInt(request.getParameter("addressId"));
                    dao.updateAddress(
                            addressId,
                            request.getParameter("detail"),
                            request.getParameter("ward"),
                            request.getParameter("district"),
                            request.getParameter("city")
                    );
                } catch (NumberFormatException ignored) {}
            }

            case "delete_address" -> {
                try {
                    int addressId = Integer.parseInt(request.getParameter("addressId"));
                    dao.deleteAddress(addressId);
                } catch (NumberFormatException ignored) {}
            }

            case "set_default_address" -> {
                try {
                    int addressId = Integer.parseInt(request.getParameter("addressId"));
                    dao.setDefaultAddress(user.getId(), addressId);
                } catch (NumberFormatException ignored) {}
            }
        }

        response.sendRedirect(request.getContextPath() + "/user/profile");
    }
}
