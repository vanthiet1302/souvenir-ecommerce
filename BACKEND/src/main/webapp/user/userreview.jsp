<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đánh giá của tôi - INOLA</title>
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
                        <span class="username"> ${sessionScope.userInSession.fullName}</span>
                    </div>
                    <ul class="user-dropdown">
                        <li><a href="${pageContext.request.contextPath}/user/userprofile.jsp">Hồ sơ của tôi</a></li>
                        <li><a href="${pageContext.request.contextPath}/user/userorder.jsp">Đơn hàng</a></li>
                        <li><a href="${pageContext.request.contextPath}/user/userfavourite.jsp">Sản phẩm yêu thích</a></li>
                        <li><a href="${pageContext.request.contextPath}/user/userreview.jsp">Đánh giá của tôi</a></li>
                        <li><a href="${pageContext.request.contextPath}/user/userpass.jsp">Đổi mật khẩu</a></li>
                        <hr>
                        <li><a href="${pageContext.request.contextPath}/logout" class="logout">Đăng xuất</a></li>
                    </ul>
                </div>
            </div>

            <div class="main-header">
                <div class="left">
                    <button id="menuBtn" class="menu-toggle"><i class="fa fa-bars"></i></button>
                    <div class="logo">
                        <a href="${pageContext.request.contextPath}/home/homepage.jsp">
                            <img src="${pageContext.request.contextPath}/assets/image/Logo/Logo-removebg-preview.png" alt="INOLA Logo" height="36">
                        </a>
                    </div>
                </div>
                <div class="center">
                    <input type="text" placeholder="Tìm kiếm sản phẩm..." class="search-bar">
                    <button class="search-btn"><i class="fa fa-search"></i></button>
                </div>
                <div class="right">
                    <div class="cart">
                        <a href="${pageContext.request.contextPath}/shoppingcart.jsp" class="cart-link">
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
                <li><a href="userprofile.jsp"><i class="fa-solid fa-user-circle"></i> Hồ Sơ Của Tôi</a></li>
                <li><a href="userorder.jsp"><i class="fa-solid fa-receipt"></i> Đơn Hàng</a></li>
                <li><a href="userfavourite.jsp"><i class="fa-solid fa-heart"></i> Sản Phẩm Yêu Thích</a></li>
                <li class="active"><a href="userreview.jsp"><i class="fa-solid fa-star"></i> Đánh Giá Của Tôi</a></li>
                <li><a href="userpass.jsp"><i class="fa-solid fa-key"></i> Đổi Mật Khẩu</a></li>
            </ul>
        </aside>

        <main class="account-content">
            <nav class="review-tabs">
                <a href="?tab=all" class="tab-item ${param.tab == 'all' || empty param.tab ? 'active' : ''}">Tất cả</a>
                <a href="?tab=pending" class="tab-item ${param.tab == 'pending' ? 'active' : ''}">Chưa đánh giá</a>
            </nav>

            <div class="review-list-container">
                <c:forEach items="${reviewList}" var="r">
                    <div class="review-card">
                        <div class="review-product-info">
                            <img src="${pageContext.request.contextPath}/assets/image/Product/${r.productImage}" alt="${r.productName}">
                            <div class="product-details">
                                <strong>${r.productName}</strong>
                                <p>Phân loại: ${r.categoryName}</p>
                            </div>
                        </div>
                        <hr class="review-divider">
                        <div class="review-content">
                            <div class="review-rating">
                                <c:forEach begin="1" end="5" var="i">
                                    <i class="${i <= r.rating ? 'fa-solid' : 'fa-regular'} fa-star"></i>
                                </c:forEach>
                            </div>
                            <span class="review-date">Đánh giá ngày ${r.reviewDate}</span>
                            <p class="review-comment">${r.comment}</p>
                        </div>
                    </div>
                </c:forEach>

                <c:if test="${empty reviewList}">
                    <div style="text-align:center; padding:50px; color:#666;">
                        <i class="fa-regular fa-comment-dots" style="font-size:48px; margin-bottom:15px;"></i>
                        <p>Bạn chưa có đánh giá nào.</p>
                    </div>
                </c:if>
            </div>
        </main>
    </div>
</div>

<jsp:include page="/views/common/footer.jsp" />
</body>
</html>