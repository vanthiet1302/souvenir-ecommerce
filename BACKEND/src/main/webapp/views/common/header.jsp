<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="header-wrapper">
    <header class="header-container page-container">

        <!-- USER BAR -->
        <div class="user-bar">
            <div class="right user-header">
                <c:choose>
                    <c:when test="${not empty sessionScope.userInSession}">
                        <div class="user-menu">
                            <div class="user-trigger">
                                <i class="fa-regular fa-user"></i>
                                <span class="username">
                                        ${sessionScope.userInSession.fullName}
                                </span>
                            </div>
                            <ul class="user-dropdown">
                                <li><a href="${pageContext.request.contextPath}/user/profile">Hồ sơ của tôi</a></li>
                                <li><a href="${pageContext.request.contextPath}/user/order">Đơn hàng</a></li>
                                <li><a href="${pageContext.request.contextPath}/user/favourite">Sản phẩm yêu thích</a></li>
                                <li><a href="${pageContext.request.contextPath}/user/review">Đánh giá của tôi</a></li>
                                <li><a href="${pageContext.request.contextPath}/forgot-password">Đổi mật khẩu</a></li>
                                <hr>
                                <li>
                                    <a href="${pageContext.request.contextPath}/logout" class="logout">
                                        Đăng xuất
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login" class="login">Đăng nhập</a>
                        <span>|</span>
                        <a href="${pageContext.request.contextPath}/signup" class="register">Đăng ký</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- MAIN HEADER -->
        <div class="main-header">
            <div class="left">
                <button id="menuBtn" class="menu-toggle">
                    <i class="fa fa-bars"></i>
                </button>
                <div class="logo">
                    <a href="${pageContext.request.contextPath}/home">
                        <img src="${pageContext.request.contextPath}/assets/images/Logo/Logo-removebg-preview.png"
                             alt="INOLA Logo" height="36">
                    </a>
                </div>
            </div>

            <div class="center">
                <input type="text" id="search"
                       placeholder="Tìm kiếm sản phẩm..."
                       class="search-bar">
                <button class="search-btn">
                    <i class="fa fa-search"></i>
                </button>
            </div>

            <div class="right">
                <div class="cart">
                    <a href="${pageContext.request.contextPath}/shoppingcart" class="cart-link">
                        <i class="fa fa-shopping-cart"></i>
                        <span class="cart-count">
                            ${not empty sessionScope.cart ? sessionScope.cart.totalQuantity() : 0}
                        </span>
                    </a>
                </div>
            </div>
        </div>

        <!-- MENU -->
        <nav class="menu-bar">
            <a href="#Loai1" class="menu-link">Quà Tặng Cá Nhân Hóa</a>
            <a href="#Loai2" class="menu-link">Hộp Quà Tặng</a>
            <a href="#Loai3" class="menu-link">Trang Sức Và Phụ Kiện</a>
            <a href="#Loai4" class="menu-link">Đồ Trang Trí Và Thủ Công</a>
            <a href="#Loai5" class="menu-link">Quà Lưu Niệm Nhỏ Gọn</a>
            <a href="#extension" class="menu-link">Mọi Người Đang Mua Gì</a>
        </nav>

    </header>
</div>
