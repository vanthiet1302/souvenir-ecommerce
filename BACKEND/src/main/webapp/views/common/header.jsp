<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="header-wrapper">
    <header class="header-container page-container">

        <div class="user-bar">
            <div class="user-header-left"> <c:choose>
                <c:when test="${not empty sessionScope.userInSession}">
                    <div class="user-menu">
                        <div class="user-trigger">
                            <i class="fa-regular fa-user"></i>
                            <span class="username">${sessionScope.userInSession.fullName}</span>
                            <i class="fa-solid fa-chevron-down" style="font-size: 10px; margin-left: 5px;"></i>
                        </div>
                        <ul class="user-dropdown">
                            <li><a href="${pageContext.request.contextPath}/user/profile">Hồ sơ của tôi</a></li>
                            <li><a href="${pageContext.request.contextPath}/user/order">Đơn hàng</a></li>
                            <li><a href="${pageContext.request.contextPath}/user/favourite">Sản phẩm yêu thích</a></li>
                            <li><a href="${pageContext.request.contextPath}/user/review">Đánh giá của tôi</a></li>
                            <li><a href="${pageContext.request.contextPath}/forgot-password">Đổi mật khẩu</a></li>
                            <hr>
                            <li><a href="${pageContext.request.contextPath}/logout" class="logout">Đăng xuất</a></li>
                        </ul>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="auth-links">
                        <a href="${pageContext.request.contextPath}/login" class="login">Đăng nhập</a>
                        <span> | </span>
                        <a href="${pageContext.request.contextPath}/signup" class="register">Đăng ký</a>
                    </div>
                </c:otherwise>
            </c:choose>
            </div>
        </div>

        <div class="main-header">
            <div class="left">
                <button id="menuBtn" class="menu-toggle"><i class="fa fa-bars"></i></button>
                <div class="logo">
                    <a href="${pageContext.request.contextPath}/home">
                        <img src="${pageContext.request.contextPath}/assets/images/Logo/Logo-removebg-preview.png" alt="INOLA Logo" height="36">
                    </a>
                </div>
            </div>

            <div class="center">
                <%-- BẮT BUỘC PHẢI CÓ FORM THÌ MỚI TÌM KIẾM ĐƯỢC --%>
                <form action="${pageContext.request.contextPath}/search" method="GET" class="search-form" style="display: flex; width: 100%;">
                    <input type="text" name="query" id="search" placeholder="Tìm kiếm sản phẩm..." class="search-bar">
                    <button type="submit" class="search-btn"><i class="fa fa-search"></i></button>
                </form>
            </div>

            <div class="right">
                <div class="cart">
                    <a href="${pageContext.request.contextPath}/shoppingcart" class="cart-link">
                        <i class="fa fa-shopping-cart"></i>
                        <span class="cart-count">${not empty sessionScope.cart ? sessionScope.cart.totalQuantity() : 0}</span>
                    </a>
                </div>
            </div>
        </div>

        <nav class="menu-bar">
            <a href="${pageContext.request.contextPath}/home#Loai1" class="menu-link">Quà Tặng Cá Nhân Hóa</a>
            <a href="${pageContext.request.contextPath}/home#Loai2" class="menu-link">Hộp Quà Tặng</a>
            <a href="${pageContext.request.contextPath}/home#Loai3" class="menu-link">Trang Sức Và Phụ Kiện</a>
            <a href="${pageContext.request.contextPath}/home#Loai4" class="menu-link">Đồ Trang Trí Và Thủ Công</a>
            <a href="${pageContext.request.contextPath}/home#Loai5" class="menu-link">Quà Lưu Niệm Nhỏ Gọn</a>
            <a href="${pageContext.request.contextPath}/home#extension" class="menu-link">Mọi Người Đang Mua Gì</a>
        </nav>
    </header>
</div>