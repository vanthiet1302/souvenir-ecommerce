<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="header-wrapper">
    <header class="header-container page-container">

        <!-- ================= USER BAR ================= -->
        <div class="user-bar">
            <div class="right header-user">

                <!-- CHƯA LOGIN -->
                <c:if test="${empty sessionScope.currentUser}">
                    <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                    <span>|</span>
                    <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
                </c:if>

                <!-- ĐÃ LOGIN -->
                <c:if test="${not empty sessionScope.currentUser}">
                    <div class="user-box" id="userToggle">
                        <i class="fa fa-user-circle"></i>
                        <span>${sessionScope.currentUser.fullName}</span>
                        <i class="fa fa-caret-down"></i>
                    </div>

                    <div class="user-dropdown" id="userDropdown">
                        <a href="${pageContext.request.contextPath}/profile">Hồ sơ</a>
                        <a href="${pageContext.request.contextPath}/orders">Đơn hàng</a>
                        <a href="${pageContext.request.contextPath}/reviews">Đánh giá</a>
                        <a href="${pageContext.request.contextPath}/change-password">Đổi mật khẩu</a>
                        <hr>
                        <a href="${pageContext.request.contextPath}/logout" class="logout">Đăng xuất</a>
                    </div>
                </c:if>

            </div>
        </div>

        <!-- ================= MAIN HEADER ================= -->
        <div class="main-header">

            <!-- LEFT -->
            <div class="left">
                <button id="menuBtn" class="menu-toggle" aria-label="Mở menu">
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
                        <img src="${pageContext.request.contextPath}/assets/images/logo.png" height="36">
                    </a>
                </div>
            </div>

            <!-- SEARCH -->
            <div class="center">
                <form action="${pageContext.request.contextPath}/search" method="get">
                    <input type="text" name="keyword" placeholder="Tìm kiếm sản phẩm...">
                    <button type="submit"><i class="fa fa-search"></i></button>
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

        <!-- ================= MENU BAR / BREADCRUMB ================= -->

        <c:choose>

            <!-- HOME -->
            <c:when test="${page eq 'HOME'}">
                <nav class="menu-bar">
                    <c:forEach var="c" items="${topCategories}" varStatus="st">
                        <a href="#Loai${st.index + 1}">${c.name}</a>
                    </c:forEach>
                    <a href="#extension">Mọi Người Đang Mua Gì</a>
                </nav>
            </c:when>

            <!-- PRODUCT TYPE -->
            <c:when test="${page eq 'PRODUCT_TYPE'}">
                <div class="breadcrumb">
                    <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
                    <span>/</span>
                    <span>${breadcrumbCategory.name}</span>
                </div>
            </c:when>

            <!-- PRODUCT DETAIL -->
            <c:when test="${page eq 'PRODUCT_DETAIL'}">
                <div class="breadcrumb">
                    <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
                    <span>/</span>
                    <a href="${pageContext.request.contextPath}/category?id=${breadcrumbCategory.id}">
                            ${breadcrumbCategory.name}
                    </a>
                    <span>/</span>
                    <span class="current">${breadcrumbProduct.name}</span>
                </div>
            </c:when>

        </c:choose>


    </header>
</div>

<div id="headerOverlay" class="overlay"></div>
