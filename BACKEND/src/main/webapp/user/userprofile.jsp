<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

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
                        <li><a href="${pageContext.request.contextPath}/user/profile">Hồ sơ của tôi</a></li>
                        <li><a href="${pageContext.request.contextPath}/user/order">Đơn hàng</a></li>
                        <li><a href="${pageContext.request.contextPath}/user/favourite">Sản phẩm yêu thích</a></li>
                        <li><a href="${pageContext.request.contextPath}/user/review">Đánh giá của tôi</a></li>
                        <li><a href="${pageContext.request.contextPath}/forgot-password">Đổi mật khẩu</a></li>
                        <hr>
                        <li><a href="${pageContext.request.contextPath}/logout" class="logout">Đăng xuất</a></li>
                    </ul>
                </div>
            </div>

            <div class="main-header">
                <div class="left">
                    <button id="menuBtn" class="menu-toggle"><i class="fa fa-bars"></i></button>
                    <div class="logo">
                        <a href="${pageContext.request.contextPath}/home">
                            <img src="${pageContext.request.contextPath}/assets/images/Logo/Logo-removebg-preview.png"
                                 alt="INOLA Logo" height="36">
                        </a>
                    </div>
                </div>
                <div class="center">
                    <input type="text" placeholder="Tìm kiếm sản phẩm..." class="search-bar">
                    <button class="search-btn"><i class="fa fa-search"></i></button>
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
                            <img src="${pageContext.request.contextPath}/assets/image/Avatar/${sessionScope.userInSession.avatar}"
                                 class="avatar-img"
                                 style="width:80px;height:80px;border-radius:50%;object-fit:cover;">
                        </c:when>
                        <c:otherwise>
                            <i class="fa-solid fa-user-circle avatar-placeholder"></i>
                        </c:otherwise>
                    </c:choose>
                </div>

                <strong>${sessionScope.userInSession.fullName}</strong>
                <span>${sessionScope.userInSession.email}</span>

                <!-- ===== FORM ĐỔI AVATAR (ĐÃ FIX) ===== -->
                <form id="avatarForm"
                      action="${pageContext.request.contextPath}/user/profile"
                      method="post"
                      enctype="multipart/form-data"
                      style="margin-top:8px;">

                    <input type="hidden" name="action" value="change_avatar">

                    <!-- input file ẨN -->
                    <input type="file"
                           id="avatarInput"
                           name="avatarFile"
                           accept="image/*"
                           style="display:none;">

                    <!-- nút duy nhất -->
                    <button type="button"
                            class="btn-change-avatar"
                            onclick="triggerAvatarUpload()">
                        Thay đổi ảnh
                    </button>
                </form>
                <!-- ===== END AVATAR ===== -->

            </div>

            <hr class="sidebar-divider">

            <ul class="account-menu">
                <li class="active"><a href="${pageContext.request.contextPath}/user/profile"><i class="fa-solid fa-user-circle"></i> Hồ Sơ Của Tôi</a></li>
                <li><a href="${pageContext.request.contextPath}/user/order"><i class="fa-solid fa-receipt"></i> Đơn Hàng</a></li>
                <li><a href="${pageContext.request.contextPath}/user/favourite"><i class="fa-solid fa-heart"></i> Sản Phẩm Yêu Thích</a></li>
                <li><a href="${pageContext.request.contextPath}/user/review"><i class="fa-solid fa-star"></i> Đánh Giá Của Tôi</a></li>
                <li><a href="${pageContext.request.contextPath}/forgot-password"><i class="fa-solid fa-key"></i> Đổi Mật Khẩu</a></li>
            </ul>
        </aside>

        <main class="account-content">
            <!-- PROFILE -->
            <div class="profile-card">
                <h2>Thông tin cá nhân</h2>

                <form class="profile-form" action="${pageContext.request.contextPath}/user/profile" method="post">
                    <input type="hidden" name="action" value="update_profile">

                    <div class="form-group">
                        <label>Họ và tên</label>
                        <input type="text" name="fullName" value="${sessionScope.userInSession.fullName}">
                    </div>

                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" value="${sessionScope.userInSession.email}" disabled>
                    </div>

                    <div class="form-group">
                        <label>Số điện thoại</label>
                        <input type="tel" name="phone" value="${sessionScope.userInSession.phone}">
                    </div>

                    <div class="form-group radio-group">
                        <label>Giới tính</label>
                        <input type="radio" name="gender" value="Nam"
                               <c:if test="${sessionScope.userInSession.gender == 'Nam'}">checked</c:if>> Nam
                        <input type="radio" name="gender" value="Nữ"
                               <c:if test="${sessionScope.userInSession.gender == 'Nữ'}">checked</c:if>> Nữ
                    </div>

                    <div class="form-group">
                        <label>Ngày sinh</label>
                        <input type="date" name="dob" value="${sessionScope.userInSession.dob}">
                    </div>

                    <button type="submit" class="btn-save">Lưu thay đổi</button>
                </form>
            </div>

            <!-- ADDRESS -->
            <div class="profile-card">
                <div class="card-header">
                    <h2>Địa chỉ</h2>
                    <button class="btn-add"
                            onclick="document.getElementById('addAddressForm').style.display='block'">
                        <i class="fa-solid fa-plus"></i> Thêm địa chỉ mới
                    </button>
                </div>

                <div id="addAddressForm"
                     style="display:none;margin:20px 0;padding:20px;border:1px dashed #5a2d81;border-radius:8px;background:#fdfbff;">
                    <form action="${pageContext.request.contextPath}/user/profile" method="post" class="profile-form">
                        <input type="hidden" name="action" value="add_address">

                        <div class="form-group">
                            <label>Địa chỉ chi tiết</label>
                            <input type="text" name="addressDetail" required>
                        </div>

                        <div style="display:grid;grid-template-columns:1fr 1fr 1fr;gap:15px;">
                            <input type="text" name="ward" placeholder="Phường/Xã" required>
                            <input type="text" name="district" placeholder="Quận/Huyện" required>
                            <input type="text" name="city" placeholder="Tỉnh/Thành phố" required>
                        </div>

                        <div style="margin-top:20px;display:flex;gap:10px;">
                            <button type="submit" class="btn-save">Thêm mới</button>
                            <button type="button" class="btn-save" style="background:#666"
                                    onclick="document.getElementById('addAddressForm').style.display='none'">
                                Hủy
                            </button>
                        </div>
                    </form>
                </div>

                <c:choose>
                    <c:when test="${not empty listAddr}">
                        <c:forEach items="${listAddr}" var="addr">
                            <div class="address-item">
                                <div class="address-info">
                                    <strong>${sessionScope.userInSession.fullName}</strong>
                                    <c:if test="${addr.isDefault == 1}">
                                        <span style="color:#5a2d81;font-weight:600;"> (Mặc định)</span>
                                    </c:if>
                                    <p class="address-text">
                                            ${addr.addressDetail}, ${addr.ward}, ${addr.district}, ${addr.city}
                                    </p>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div style="text-align:center;padding:20px;color:#999;">
                            Bạn chưa có địa chỉ nào.
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
    </div>
</div>

<jsp:include page="/views/common/footer.jsp"/>

<!-- ===== JS ĐỔI AVATAR ===== -->
<script>
    function triggerAvatarUpload() {
        document.getElementById('avatarInput').click();
    }

    document.getElementById('avatarInput').addEventListener('change', function () {
        if (this.files.length > 0) {
            document.getElementById('avatarForm').submit();
        }
    });
</script>

</body>
</html>
