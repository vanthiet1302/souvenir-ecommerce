<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hồ sơ của tôi - INOLA</title>
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
                <li class="active"><a href="userprofile.jsp" ><i class="fa-solid fa-user-circle"></i> Hồ Sơ Của Tôi</a></li>
                <li><a href="userorder.jsp"><i class="fa-solid fa-receipt"></i> Đơn Hàng</a></li>
                <li><a href="userfavourite.jsp"><i class="fa-solid fa-heart"></i> Sản Phẩm Yêu Thích</a></li>
                <li><a href="userreview.jsp"><i class="fa-solid fa-star"></i> Đánh Giá Của Tôi</a></li>
                <li><a href="userpass.jsp"><i class="fa-solid fa-key"></i> Đổi Mật Khẩu</a></li>
            </ul>
        </aside>

        <main class="account-content">
            <div class="profile-card">
                <h2>Thông tin cá nhân</h2>

                <c:if test="${not empty message}">
                    <div style="color: green; margin-bottom: 15px;">${message}</div>
                </c:if>

                <form class="profile-form" action="${pageContext.request.contextPath}/user/update-profile" method="post">
                    <div class="form-group">
                        <label for="fullname">Họ và tên</label>
                        <input type="text" id="fullname" name="fullname" value="${sessionScope.userInSession.fullName}">
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" value="${sessionScope.userInSession.email}" disabled>
                        <input type="hidden" name="email" value="${sessionScope.userInSession.email}">
                    </div>
                    <div class="form-group">
                        <label for="phone">Số điện thoại</label>
                        <input type="tel" id="phone" name="phone" value="${sessionScope.userInSession.phone}">
                    </div>
                    <div class="form-group radio-group">
                        <label>Giới tính</label>
                        <input type="radio" id="male" name="gender" value="Nam" ${sessionScope.userInSession.gender == 'Nam' ? 'checked' : ''}> <label for="male">Nam</label>
                        <input type="radio" id="female" name="gender" value="Nữ" ${sessionScope.userInSession.gender == 'Nữ' ? 'checked' : ''}> <label for="female">Nữ</label>
                    </div>
                    <div class="form-group">
                        <label for="dob">Ngày sinh</label>
                        <input type="date" id="dob" name="dob" value="${sessionScope.userInSession.dob}">
                    </div>
                    <button type="submit" class="btn-save">Lưu thay đổi</button>
                </form>
            </div>

            <div class="profile-card">
                <div class="card-header">
                    <h2>Địa chỉ</h2>
                    <button class="btn-add"><i class="fa-solid fa-plus"></i> Thêm địa chỉ mới</button>
                </div>

                <div class="address-item">
                    <div class="address-info">
                        <div class="info-header">
                            <strong>${sessionScope.userInSession.fullName}</strong>
                            <span class="default-badge">Mặc định</span>
                        </div>
                        <p class="phone-number">SĐT: ${sessionScope.userInSession.phone}</p>
                        <p class="address-text">Kiên Ngãi, Bình An, Gia Lai</p>
                    </div>
                    <div class="address-actions">
                        <button class="btn-link">Cập nhật</button>
                        <button class="btn-link btn-delete">Xóa</button>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<jsp:include page="/views/common/footer.jsp" />
</body>
</html>