<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="header-wrapper">
    <header class="header-container page-container">

        <!-- USER BAR -->
        <div class="user-bar">
            <div class="right">
                <c:choose>
                    <c:when test="${not empty authUser}">
                        <span>Xin chào, ${authUser.fullName}</span>
                        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                        <span>|</span>
                        <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
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

                <!-- ALL CATEGORIES -->
                <div class="dropdown-menu" id="dropdownMenu">
                    <c:forEach var="c" items="${categories}">
                        <a href="${pageContext.request.contextPath}/product-type?id=${c.id}">
                                ${c.name}
                        </a>
                    </c:forEach>
                </div>

                <!-- LOGO -->
                <div class="logo">
                    <a href="${pageContext.request.contextPath}/home">
                        <img src="${pageContext.request.contextPath}/assets/images/logo.png"
                             alt="INOLA" height="36">
                    </a>
                </div>
            </div>

            <!-- SEARCH -->
            <div class="center">
                <form action="${pageContext.request.contextPath}/search" method="get">
                    <input type="text" name="keyword" placeholder="Tìm kiếm sản phẩm...">
                    <button type="submit">
                        <i class="fa fa-search"></i>
                    </button>
                </form>
            </div>

            <!-- CART -->
            <div class="right">
                <a href="${pageContext.request.contextPath}/cart">
                    <i class="fa fa-shopping-cart"></i>
                    <span class="cart-count">0</span>
                </a>
            </div>
        </div>

        <!-- TOP CATEGORY BAR -->
        <nav class="menu-bar">
            <c:forEach var="c" items="${topCategories}">
                <a href="${pageContext.request.contextPath}/product-type?id=${c.id}">
                        ${c.name}
                </a>
            </c:forEach>
        </nav>

    </header>
</div>
