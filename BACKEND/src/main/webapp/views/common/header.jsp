<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    /* ===== USER DROPDOWN ===== */
    .user-bar {
        position: relative;
    }

    .user-box {
        cursor: pointer;
        user-select: none;
        display: flex;
        align-items: center;
        gap: 6px;
        font-weight: 500;
    }

    .user-dropdown {
        display: none;
        position: absolute;
        top: 38px;
        right: 0;
        background: #fff;
        min-width: 200px;
        box-shadow: 0 8px 20px rgba(0,0,0,0.15);
        border-radius: 8px;
        z-index: 999;
        overflow: hidden;
    }

    .user-dropdown.show {
        display: block;
    }

    .user-dropdown a {
        display: flex;
        align-items: center;
        gap: 10px;
        padding: 10px 14px;
        color: #333;
        text-decoration: none;
        font-size: 14px;
    }

    .user-dropdown a:hover {
        background: #f6f6f6;
    }

    .user-dropdown .logout {
        color: #c0392b;
    }

    .dropdown-divider {
        height: 1px;
        background: #eee;
        margin: 6px 0;
    }
</style>

<div class="header-wrapper">
    <header class="header-container page-container">

        <!-- ===== USER BAR ===== -->
        <div class="user-bar">
            <div class="right">
                <c:choose>
                    <c:when test="${not empty sessionScope.userInSession}">
                        <div class="user-box" id="userToggle">
            <span class="user-name">
                Xin chào, ${sessionScope.userInSession.fullName}
            </span>
                            <i class="fa fa-caret-down"></i>
                        </div>

                        <div class="user-dropdown" id="userDropdown">

                            <a href="${pageContext.request.contextPath}/user/profile">
                                <i class="fa fa-user"></i> Tài khoản
                            </a>

                            <a href="${pageContext.request.contextPath}/user/orders">
                                <i class="fa fa-receipt"></i> Đơn hàng
                            </a>

                            <a href="${pageContext.request.contextPath}/user/review">
                                <i class="fa fa-star"></i> Đánh giá
                            </a>

                            <div class="dropdown-divider"></div>

                            <a href="${pageContext.request.contextPath}/logout" class="logout">
                                <i class="fa fa-sign-out-alt"></i> Đăng xuất
                            </a>
                        </div>
                    </c:when>

                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                        <span>|</span>
                        <a href="${pageContext.request.contextPath}/signup">Đăng ký</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- ===== MAIN HEADER ===== -->
        <div class="main-header">

            <div class="left">
                <button id="menuBtn" class="menu-toggle">
                    <i class="fa fa-bars"></i>
                </button>

                <div class="dropdown-menu" id="dropdownMenu">
                    <c:forEach var="c" items="${categories}">
                        <a href="${pageContext.request.contextPath}/product-type?id=${c.id}">
                                ${c.name}
                        </a>
                    </c:forEach>
                </div>

                <div class="logo">
                    <a href="${pageContext.request.contextPath}/home">
                        <img src="${pageContext.request.contextPath}/assets/images/Logo/Logo-removebg-preview.png"
                             alt="INOLA Logo">
                    </a>
                </div>
            </div>

            <div class="center">
                <form action="${pageContext.request.contextPath}/search" method="get" class="search-wrapper">
                    <input type="text" name="keyword" class="search-bar" placeholder="Tìm kiếm sản phẩm...">
                    <button type="submit" class="search-btn">
                        <i class="fa fa-search"></i>
                    </button>
                </form>
            </div>

            <div class="right">
                <a href="${pageContext.request.contextPath}/cart" class="cart">
                    <i class="fa fa-shopping-cart"></i>
                    <c:if test="${cartItemCount > 0}">
                        <span class="cart-count">${cartItemCount}</span>
                    </c:if>
                </a>
            </div>

        </div>

        <!-- ===== MENU / BREADCRUMB ===== -->
        <c:if test="${headerMode == null || headerMode == 'MENU'}">
            <nav class="menu-bar">
                <c:forEach var="c" items="${topCategories}">
                    <a href="${pageContext.request.contextPath}/product-type?id=${c.id}">
                            ${c.name}
                    </a>
                </c:forEach>
            </nav>
        </c:if>

        <c:if test="${headerMode == 'BREADCRUMB'}">
            <div class="breadcrumb">
                <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
                <span>›</span>
                <span>${breadcrumbCategory.name}</span>
                <span>›</span>
                <span>${breadcrumbProduct.name}</span>
            </div>
        </c:if>

    </header>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const userToggle = document.getElementById("userToggle");
        const userDropdown = document.getElementById("userDropdown");

        if (userToggle && userDropdown) {
            userToggle.addEventListener("click", function (e) {
                e.stopPropagation();
                userDropdown.classList.toggle("show");
            });

            document.addEventListener("click", function () {
                userDropdown.classList.remove("show");
            });
        }
    });
</script>

<div id="headerOverlay" class="overlay"></div>
