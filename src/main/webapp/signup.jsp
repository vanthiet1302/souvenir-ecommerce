<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng ký - INOLA</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/signup.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/HomePageFooter.css">
    <style>
        .error-text { color: #ff4d4d; text-align: center; font-weight: bold; margin-bottom: 10px; }
        .success-text { color: #2ecc71; text-align: center; font-weight: bold; margin-bottom: 10px; }
        .eye_icon { cursor: pointer; position: absolute; right: 15px; top: 50%; transform: translateY(-50%); z-index: 10; }
        .input_group { position: relative; }
    </style>
</head>

<body>
<div class="main_content">
    <div class="split_panel left_panel">
        <h1>Chào mừng trở lại!</h1>
        <p>Để tiếp tục kết nối với chúng tôi, vui lòng đăng nhập bằng thông tin cá nhân của bạn.</p>
        <a href="${pageContext.request.contextPath}/login" class="login_button">ĐĂNG NHẬP</a>
    </div>

    <div class="split_panel right_panel">
        <h2>Tạo tài khoản</h2>
        <div class="social_login">
            <button class="social_icon facebook"><i class="fab fa-facebook-f"></i></button>
            <button class="social_icon google"><i class="fab fa-google-plus-g"></i></button>
            <button class="social_icon linkedin"><i class="fab fa-linkedin-in"></i></button>
        </div>
        <p class="separator">hoặc sử dụng email để đăng ký</p>

        <%-- Hiển thị thông báo lỗi hoặc thành công từ Controller --%>
        <c:if test="${not empty error}"><p class="error-text">${error}</p></c:if>
        <c:if test="${not empty success}"><p class="success-text">${success}</p></c:if>

        <form class="signup_form" action="${pageContext.request.contextPath}/signup" method="post">
            <div class="input_group">
                <input type="text" name="full_name" placeholder=" " id="full_name" required>
                <label for="full_name">Họ và Tên</label>
            </div>

            <div class="input_group">
                <input type="email" name="email" placeholder=" " id="email" required>
                <label for="email">Địa chỉ Email</label>
            </div>

            <div class="input_group">
                <input type="tel" name="phone" placeholder=" " id="phone" pattern="[0-9]{10,20}" required>
                <label for="phone">Số điện thoại</label>
            </div>

            <div class="input_group">
                <input type="password" name="password" placeholder=" " id="matkhau" minlength="8" required>
                <label for="matkhau">Mật khẩu (Ít nhất 8 ký tự)</label>
                <span class="eye_icon" onclick="togglePassword('matkhau', this)">
                    <i class="fa fa-eye"></i>
                </span>
            </div>

            <div class="input_group">
                <input type="password" name="confirm_password" placeholder=" " id="confirm_password" minlength="8" required>
                <label for="confirm_password">Xác nhận mật khẩu</label>
                <span class="eye_icon" onclick="togglePassword('confirm_password', this)">
                    <i class="fa fa-eye"></i>
                </span>
            </div>

            <%-- Nút đăng ký đã được thêm chữ Tiếng Việt --%>
            <button type="submit" class="signup_button">ĐĂNG KÝ</button>
        </form>
    </div>
</div>

<jsp:include page="/views/common/footer.jsp" />

<script>
    function togglePassword(inputId, iconContainer) {
        const input = document.getElementById(inputId);
        const icon = iconContainer.querySelector('i');
        input.type = (input.type === "password") ? "text" : "password";
        icon.classList.toggle('fa-eye');
        icon.classList.toggle('fa-eye-slash');
    }
</script>
</body>
</html>