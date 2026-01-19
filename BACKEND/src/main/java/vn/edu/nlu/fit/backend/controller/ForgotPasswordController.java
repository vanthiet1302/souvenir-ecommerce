package vn.edu.nlu.fit.backend.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.backend.dao.UserDAO;
import java.io.IOException;

@WebServlet(name = "ForgotPasswordController", value = "/forgot-password")
public class ForgotPasswordController extends HttpServlet {
    private UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("step", 1);
        request.getRequestDispatcher("/user/user-pass.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        int step = 1;

        if ("send_code".equals(action)) {
            // Bước 1: Nhận account và gửi mã. Thêm .trim() để sạch dữ liệu
            String accountInfo = request.getParameter("account_info");
            if (accountInfo != null) accountInfo = accountInfo.trim();

            String code = String.valueOf((int) (Math.random() * 900000) + 100000);

            if (dao.setResetCode(accountInfo, code)) {
                System.out.println(">>> OTP INOLA: " + code);
                session.setAttribute("resetAccount", accountInfo);
                step = 2;
            } else {
                request.setAttribute("error", "Email hoặc Số điện thoại không tồn tại!");
                step = 1;
            }

        } else if ("verify_code".equals(action)) {
            // Bước 2: Kiểm tra mã OTP
            String[] digits = request.getParameterValues("digit");
            String fullCode = (digits != null) ? String.join("", digits) : "";
            String accountInfo = (String) session.getAttribute("resetAccount");

            if (dao.verifyCode(accountInfo, fullCode)) {
                step = 3;
            } else {
                request.setAttribute("error", "Mã xác nhận không đúng hoặc đã hết hạn!");
                step = 2;
            }

        } else if ("update_password".equals(action)) {
            // Bước 3: Cập nhật pass mới
            String newPass = request.getParameter("new_password");
            String confirmPass = request.getParameter("confirm_password");
            String accountInfo = (String) session.getAttribute("resetAccount");

            // Kiểm tra session và mật khẩu khớp nhau
            if (accountInfo == null) {
                request.setAttribute("error", "Phiên làm việc hết hạn, vui lòng làm lại từ đầu.");
                step = 1;
            } else if (newPass != null && newPass.equals(confirmPass)) {
                if (dao.updatePassword(accountInfo, newPass)) {
                    session.removeAttribute("resetAccount"); // Xóa rác session
                    request.setAttribute("success", "Đã đổi mật khẩu thành công! Mời bạn đăng nhập.");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                    return;
                }
            } else {
                request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
                step = 3;
            }
        }

        request.setAttribute("step", step);
        request.getRequestDispatcher("/user/user-pass.jsp").forward(request, response);
    }
}