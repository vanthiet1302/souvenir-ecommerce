<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập - INOLA</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/HomePageFooter.css">
    <style>
        .error-message { color: #ff4d4d; background: #fff1f0; border: 1px solid #ffa39e; padding: 10px; border-radius: 4px; margin-bottom: 15px; }
    </style>
</head>
<body>
<div class="pixfort_login_1">
    <div class="container">
        <div class="login_wrapper">
            <div class="login_left">
                <h2 class="login_title">ĐĂNG NHẬP</h2>
                <div class="login_illustration">
                    <img src="https://cdn-icons-png.flaticon.com/512/4140/4140048.png" alt="User Illustration">
                </div>

                <%-- Hiển thị thông báo lỗi từ request --%>
                <c:if test="${not empty error}">
                    <div class="error-message">
                        <i class="fa fa-exclamation-circle"></i> ${error}
                    </div>
                </c:if>

                <%-- Hiển thị thông báo thành công từ request --%>
                <c:if test="${not empty success}">
                    <div class="success-message" style="color: #52c41a; background: #f6ffed; border: 1px solid #b7eb8f; padding: 10px; border-radius: 4px; margin-bottom: 15px;">
                        <i class="fa fa-check-circle"></i> ${success}
                    </div>
                </c:if>

                <%-- Action trỏ về Servlet /login thay vì file vật lý --%>
                <form class="login_form" action="${pageContext.request.contextPath}/login" method="post">
                    <div class="input_group">
                        <i class="fa fa-envelope"></i>
                        <input type="text" name="loginDetail" placeholder="Email hoặc Số điện thoại" required>
                    </div>
                    <div class="input_group">
                        <i class="fa fa-lock"></i>
                        <input type="password" name="password" placeholder="Mật khẩu" required>
                    </div>

                    <div class="forgot_pass">
                        <%-- Sửa link trỏ về Servlet ForgotPasswordController --%>
                        <a href="${pageContext.request.contextPath}/forgot-password">Quên mật khẩu?</a>
                    </div>

                    <button type="submit" class="btn_login">ĐĂNG NHẬP</button>

                    <button type="button" class="btn_google light_bg">
                        <i class="fa-brands fa-google"></i> Đăng ký với Google
                    </button>


                    <a href="${pageContext.request.contextPath}/signup" class="btn_register light_bg" style="text-decoration: none; display: block; text-align: center;">
                        <i class="fa fa-user-plus"></i> Đăng ký tài khoản mới
                    </a>
                </form>
            </div>
            <div class="login_right"></div>
        </div>
    </div>
</div>

<jsp:include page="/views/common/footer.jsp" />

</body>
</html>