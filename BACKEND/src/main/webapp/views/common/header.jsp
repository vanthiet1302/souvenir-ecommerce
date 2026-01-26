<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="header-wrapper">
    <header class="header-container page-container">

        <!-- ===== USER BAR ===== -->
        <div class="user-bar">
            <div class="right">
                <c:choose>
                    <c:when test="${not empty authUser}">
                        <div class="user-box" id="userToggle">
                            <span>Xin chào, ${authUser.fullName}</span>
                            <i class="fa fa-caret-down"></i>
                        </div>

                        <div class="user-dropdown" id="userDropdown">
                            <a href="${pageContext.request.contextPath}/profile">Tài khoản</a>
                            <a href="${pageContext.request.contextPath}/orders">Đơn hàng</a>
                            <a href="${pageContext.request.contextPath}/logout" class="logout">Đăng xuất</a>
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

            <!-- LEFT -->
            <div class="left">
                <button id="menuBtn" class="menu-toggle">
                    <i class="fa fa-bars"></i>
                </button>

                <div class="dropdown-menu" id="dropdownMenu" aria-hidden="true">
                    <c:forEach var="c" items="${categories}">
                        <a href="${pageContext.request.contextPath}/product-type?id=${c.id}">
                                ${c.name}
                        </a>
                    </c:forEach>
                </div>

                <div class="logo">
                    <a href="${pageContext.request.contextPath}/home">
                        <img src="${pageContext.request.contextPath}/assets/images/logo/Logo-removebg-preview.png"
                             alt="INOLA Logo">
                    </a>
                </div>
            </div>

            <!-- CENTER -->
            <div class="center">
                <div class="search-wrapper">
                    <form action="${pageContext.request.contextPath}/search" method="get">
                        <input type="text" name="keyword" class="search-bar"
                               placeholder="Tìm kiếm sản phẩm...">
                        <button type="submit" class="search-btn">
                            <i class="fa fa-search"></i>
                        </button>
                    </form>
                </div>
            </div>

            <!-- RIGHT -->
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

<div id="headerOverlay" class="overlay"></div>
