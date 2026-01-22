<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đơn hàng của tôi - INOLA</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/HomePageFooter.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/User.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/HomePageAlter.css">
</head>
<body>
<div class="page-container">
    <div class="header-wrapper">
        <header class="header-container page-container">
            <div class="right user-header">
                <div class="user-menu">
                    <div class="user-trigger">
                        <i class="fa-regular fa-user"></i>
                        <span class="username">${sessionScope.userInSession.fullName}</span>
                    </div>
                    <ul class="user-dropdown">

                        <li><a href="${pageContext.request.contextPath}/user/profile">Hồ sơ của tôi</a></li>
                        <li><a href="${pageContext.request.contextPath}/user/order">Đơn hàng</a></li>
                        <li><a href="${pageContext.request.contextPath}/user/favourite">Sản phẩm yêu thích</a></li>
                        <li><a href="${pageContext.request.contextPath}/forgot-password">Đổi mật khẩu</a></li>
                        <li><a href="${pageContext.request.contextPath}/logout" class="logout">Đăng xuất</a></li>
                    </ul>
                </div>
            </div>

            <div class="main-header">
                <div class="left">
                    <div class="logo">
                        <%-- Chuyển link Logo về Servlet Home [cite: 24, 25] --%>
                        <a href="${pageContext.request.contextPath}/home">
                            <img src="${pageContext.request.contextPath}/assets/image/Logo/Logo-removebg-preview.png" alt="INOLA Logo" height="36">
                        </a>
                    </div>
                </div>

                <div class="center">
                    <form action="${pageContext.request.contextPath}/search" method="get" style="display:flex; width:100%;">
                        <input type="text" name="query" placeholder="Tìm kiếm đơn hàng..." class="search-bar">
                        <button type="submit" class="search-btn"><i class="fa fa-search"></i></button>
                    </form>
                </div>

                <div class="right">
                    <div class="cart">
                        <a href="${pageContext.request.contextPath}/shoppingcart" class="cart-link">
                            <i class="fa fa-shopping-cart"></i>
                            <span class="cart-count">0</span>
                        </a>
                    </div>
                </div>
            </div>
        </header>
    </div>

    <div class="account-container">
        <aside class="account-sidebar">
            <div class="sidebar-profile">
                <div class="avatar-container">
                    <c:choose>
                        <c:when test="${not empty sessionScope.userInSession.avatar}">
                            <img src="${pageContext.request.contextPath}/assets/image/Avatar/${sessionScope.userInSession.avatar}" alt="Avatar" class="avatar-img" style="width:80px; height:80px; border-radius:50%; object-fit: cover;">
                        </c:when>
                        <c:otherwise>
                            <i class="fa-solid fa-user-circle avatar-placeholder"></i>
                        </c:otherwise>
                    </c:choose>
                </div>
                <strong>${sessionScope.userInSession.fullName}</strong>
                <span>${sessionScope.userInSession.email}</span>
                <button class="btn-change-avatar">Thay đổi ảnh</button>
            </div>

            <hr class="sidebar-divider">

            <ul class="account-menu">
                <%-- Đồng bộ Sidebar với các Servlet URL [cite: 34, 35] --%>
                <li><a href="${pageContext.request.contextPath}/user/profile"><i class="fa-solid fa-user-circle"></i> Hồ Sơ Của Tôi</a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/user/order"><i class="fa-solid fa-receipt"></i> Đơn Hàng</a></li>
                <li><a href="${pageContext.request.contextPath}/user/favourite"><i class="fa-solid fa-heart"></i> Sản Phẩm Yêu Thích</a></li>
                <li><a href="${pageContext.request.contextPath}/user/review"><i class="fa-solid fa-star"></i> Đánh Giá Của Tôi</a></li>
                <li><a href="${pageContext.request.contextPath}/forgot-password"><i class="fa-solid fa-key"></i> Đổi Mật Khẩu</a></li>
            </ul>
        </aside>

        <main class="account-content">
            <nav class="order-tabs">
                <%-- Loại bỏ .jsp trong các tab lọc trạng thái [cite: 36, 37, 38] --%>
                <a href="${pageContext.request.contextPath}/user/order?status=all" class="tab-item ${param.status == 'all' || empty param.status ? 'active' : ''}">Tất cả</a>
                <a href="${pageContext.request.contextPath}/user/order?status=pending" class="tab-item ${param.status == 'pending' ? 'active' : ''}">Chờ xác nhận</a>
                <a href="${pageContext.request.contextPath}/user/order?status=shipping" class="tab-item ${param.status == 'shipping' ? 'active' : ''}">Đang giao</a>
                <a href="${pageContext.request.contextPath}/user/order?status=delivered" class="tab-item ${param.status == 'delivered' ? 'active' : ''}">Đã giao</a>
                <a href="${pageContext.request.contextPath}/user/order?status=cancelled" class="tab-item ${param.status == 'cancelled' ? 'active' : ''}">Đã Hủy</a>
            </nav>

            <div class="order-content">
                <div class="table-responsive">
                    <table class="order-list-table">
                        <thead>
                        <tr>
                            <th>MÃ ĐƠN HÀNG</th>
                            <th>SẢN PHẨM</th>
                            <th>NGÀY ĐẶT</th>
                            <th>TỔNG TIỀN</th>
                            <th>TRẠNG THÁI</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${orderList}" var="o">
                            <tr>
                                <td><a href="${pageContext.request.contextPath}/user/orderdetail?id=${o.id}">#${o.id}</a></td>
                                <td>
                                    <div class="product-cell">
                                        <img src="${pageContext.request.contextPath}/assets/image/Product/${o.image}" alt="Sản phẩm">
                                        <div class="product-info">
                                            <strong>${o.productName}</strong>
                                            <span>x${o.quantity}</span>
                                        </div>
                                    </div>
                                </td>
                                <td>${o.orderDate}</td>
                                <td><fmt:formatNumber value="${o.totalPrice}" type="currency" currencySymbol="đ" /></td>
                                <td>
                                    <div class="badge ${o.statusClass}">${o.statusName}</div>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty orderList}">
                            <tr>
                                <td colspan="5" style="text-align: center; padding: 40px; color: #999;">
                                    Bạn chưa có đơn hàng nào.
                                </td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>
</div>

<jsp:include page="/views/common/footer.jsp" />
</body>
</html>