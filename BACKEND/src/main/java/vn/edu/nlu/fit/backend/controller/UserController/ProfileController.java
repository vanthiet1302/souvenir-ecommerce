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

@WebServlet(name = "ProfileController", value = "/user/profile")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
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
        request.getRequestDispatcher("/user/userprofile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

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

            case "update_profile": {
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
                    request.setAttribute("message", "Cập nhật thông tin thành công!");
                }
                break;
            }

            case "change_avatar": {
                Part filePart = request.getPart("avatarFile");

                if (filePart != null && filePart.getSize() > 0) {

                    String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String ext = originalFileName.substring(originalFileName.lastIndexOf("."));

                    String fileName = "avatar_" + user.getId() + "_" + System.currentTimeMillis() + ext;

                    String uploadPath = getServletContext().getRealPath("/assets/image/Avatar");
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) uploadDir.mkdirs();

                    filePart.write(uploadPath + File.separator + fileName);

                    if (dao.updateAvatar(user.getId(), fileName)) {
                        user.setAvatar(fileName);
                        session.setAttribute("userInSession", user);
                        request.setAttribute("message", "Cập nhật ảnh đại diện thành công!");
                    }
                }
                break;
            }

            case "add_address": {
                dao.addAddress(
                        user.getId(),
                        request.getParameter("addressDetail"),
                        request.getParameter("city"),
                        request.getParameter("district"),
                        request.getParameter("ward")
                );
                request.setAttribute("message", "Thêm địa chỉ thành công!");
                break;
            }

            case "update_address": {
                int addressId = Integer.parseInt(request.getParameter("addressId"));
                dao.updateAddress(
                        addressId,
                        request.getParameter("addressDetail"),
                        request.getParameter("ward"),
                        request.getParameter("district"),
                        request.getParameter("city")
                );
                request.setAttribute("message", "Cập nhật địa chỉ thành công!");
                break;
            }

            case "delete_address": {
                int addressId = Integer.parseInt(request.getParameter("addressId"));
                dao.deleteAddress(addressId);
                request.setAttribute("message", "Xóa địa chỉ thành công!");
                break;
            }

            case "set_default_address": {
                int addressId = Integer.parseInt(request.getParameter("addressId"));
                dao.setDefaultAddress(user.getId(), addressId);
                request.setAttribute("message", "Đã đặt địa chỉ mặc định!");
                break;
            }
        }

        request.setAttribute("listAddr", dao.getAddressesByUserId(user.getId()));
        request.getRequestDispatcher("/user/userprofile.jsp").forward(request, response);
    }
}
