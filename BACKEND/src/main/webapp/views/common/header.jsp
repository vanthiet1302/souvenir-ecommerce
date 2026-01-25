<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="header-wrapper">
    <header class="header-container">

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
                        <a href="${pageContext.request.contextPath}/signup">Đăng ký</a>
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

                <div class="dropdown-menu" id="dropdownMenu">
                    <c:forEach var="c" items="${categories}">
                        <a href="${pageContext.request.contextPath}/product-type?id=${c.id}">
                                ${c.name}
                        </a>
                    </c:forEach>
                </div>

                <div class="logo">
                    <a href="${pageContext.request.contextPath}/home">
                        <img src="${pageContext.request.contextPath}/assets/images/logo.png" alt="INOLA">
                    </a>
                </div>
            </div>

            <div class="center">
                <form action="${pageContext.request.contextPath}/search" method="get">
                    <input type="text" name="keyword" placeholder="Tìm kiếm sản phẩm...">
                </form>
            </div>

            <div class="right">
                <a href="${pageContext.request.contextPath}/cart">
                    <i class="fa fa-shopping-cart"></i>
                </a>
            </div>
        </div>

        <!-- MENU / BREADCRUMB -->
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
