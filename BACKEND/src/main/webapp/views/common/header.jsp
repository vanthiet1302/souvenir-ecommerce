<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/SearchAutocomplete.css">
<input type="hidden"
       id="contextPath"
       value="${pageContext.request.contextPath}">
<div class="header-wrapper">
    <header class="header-container page-container">

        <!-- USER BAR -->
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

        <!-- MAIN HEADER -->
        <div class="main-header">

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

            <div class="center">
                <form action="${pageContext.request.contextPath}/search"
                      method="get"
                      class="search-wrapper"
                      style="position: relative;">

                    <input type="text"
                           name="keyword"
                           class="search-bar"
                           placeholder="Tìm kiếm sản phẩm...">

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
                    <c:choose>

                        <c:when test="${headerMode == 'MENU'}">
                            <a href="#Loai${c.id}">
                                    ${c.name}
                            </a>
                        </c:when>

                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/home#Loai${c.id}">
                                    ${c.name}
                            </a>
                        </c:otherwise>

                    </c:choose>
                </c:forEach>
            </nav>
        </c:if>

        <c:if test="${headerMode == 'BREADCRUMB'}">
            <div class="breadcrumb">

                <!-- HOME -->
                <a href="${pageContext.request.contextPath}/home">
                    Trang chủ
                </a>

                <span>›</span>

                <!-- CATEGORY -->
                <c:if test="${not empty breadcrumbCategory}">
                    <a href="${pageContext.request.contextPath}/category?id=${breadcrumbCategory.id}">
                            ${breadcrumbCategory.name}
                    </a>
                </c:if>

                <!-- PRODUCT (OPTIONAL) -->
                <c:if test="${not empty breadcrumbProduct}">
                    <span>›</span>
                    <span class="current">
                            ${breadcrumbProduct.name}
                    </span>
                </c:if>

            </div>
        </c:if>

    </header>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const userToggle = document.getElementById("userToggle");
        const userDropdown = document.getElementById("userDropdown");

        if (userToggle && userDropdown) {

            console.log("User menu initialized");

            userToggle.addEventListener("click", function (e) {
                e.preventDefault();
                e.stopPropagation();

                const isShowing = userDropdown.classList.contains("show");
                if (isShowing) {
                    userDropdown.classList.remove("show");
                    userDropdown.style.display = "none";
                } else {
                    userDropdown.classList.add("show");
                    userDropdown.style.display = "block";
                }
            });

            document.addEventListener("click", function (e) {
                if (!userToggle.contains(e.target) && !userDropdown.contains(e.target)) {
                    userDropdown.classList.remove("show");
                    userDropdown.style.display = "none";
                }
            });
        }
    });
</script>

<c:if test="${enableHeaderOverlay}">
    <div id="headerOverlay" class="overlay"></div>
</c:if>

<script src="${pageContext.request.contextPath}/assets/js/SearchAutocomplete.js"></script>
