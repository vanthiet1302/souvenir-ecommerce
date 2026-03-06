<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style> .center {
    flex: 1;
    max-width: 600px;
    margin: 0 30px;
}


.search-wrapper {
    position: relative;
    width: 100%;
}


.search-bar {
    width: 100%;
    height: 42px;
    padding: 0 50px 0 20px;

    border: 2px solid #2db89b;
    border-radius: 25px;

    font-size: 14px;
    outline: none;             /
    transition: all 0.3s ease;
}


.search-bar:focus {
    border-color: #24967e;
    box-shadow: 0 0 8px rgba(45, 184, 155, 0.2); /
}


.search-btn {
    position: absolute;
    top: 50%;
    right: 4px;
    transform: translateY(-50%);
    width: 34px;
    height: 34px;

    background-color: #2db89b;
    color: #ffffff;
    border: none;
    border-radius: 50%;

    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: background 0.2s;
}

.search-btn:hover {
    background-color: #24967e;
}

.search-btn i {
    font-size: 14px;
}
.menu-bar {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    gap: 30px;

    padding: 15px 0;
    margin-top: 15px;
    border-top: 1px solid #f0f0f0;
    background-color: #fff;
}

.menu-bar a {
    font-size: 14px;
    font-weight: 700;
    color: #444;
    text-transform: uppercase;
    position: relative;
    transition: color 0.3s ease;
    padding: 5px 0;
}


.menu-bar a:hover {
    color: #2db89b;
    cursor: pointer;
}


.menu-bar a::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 0%;
    height: 2px;
    background-color: #2db89b;
    transition: width 0.3s ease;
}

.menu-bar a:hover::after {
    width: 100%;
}


.breadcrumb {
    padding: 12px 0;
    margin-top: 10px;
    background-color: #f9f9f9;
    font-size: 14px;
    color: #666;
    display: flex;
    align-items: center;
    gap: 8px;
}

.header-container .breadcrumb {
    padding-left: 0;
}

.breadcrumb a {
    color: #333;
    transition: 0.2s;
}

.breadcrumb a:hover {
    color: #2db89b;
}

.breadcrumb span {
    color: #999;
    font-size: 12px;
}

.breadcrumb .current {
    color: #2db89b;
    font-weight: 600;
}
.user-dropdown {
    display: none;
    position: absolute;
    top: 100%;
    right: 0;
    margin-top: 10px;
    width: 240px;
    background: #ffffff;
    box-shadow: 0 5px 20px rgba(0,0,0,0.15);
    border-radius: 8px;
    border: 1px solid #eee;
    flex-direction: column;
    padding: 8px 0;
    z-index: 9999;
}

.user-dropdown.show,
.user-dropdown.open {
    display: flex !important;
}

.user-dropdown::before {
    content: "";
    position: absolute;
    top: -6px;
    right: 20px;
    width: 12px;
    height: 12px;
    background: #fff;
    transform: rotate(45deg);
    border-top: 1px solid #eee;
    border-left: 1px solid #eee;
}

.user-dropdown a {
    display: flex !important;
    align-items: center;
    width: 100%;
    padding: 12px 20px;
    font-size: 14px;
    font-weight: 500;
    color: #444;
    text-decoration: none;
    border-bottom: 1px solid #f7f7f7;
    transition: all 0.2s ease;
}

.user-dropdown a:last-child {
    border-bottom: none;
}

.user-dropdown a:hover {
    background-color: #f0fdfa;
    color: #2db89b;
    padding-left: 25px;
}

.user-dropdown a i {
    width: 25px;
    text-align: center;
    margin-right: 12px;
    font-size: 16px;
    color: #999;
    transition: color 0.2s;
}

.user-dropdown a:hover i {
    color: #2db89b;
}

.user-dropdown .logout {
    color: #ff4757;
}

.user-dropdown .logout:hover {
    background-color: #fff5f6;
    color: #ff4757;
}

.user-dropdown .logout i {
    color: #ff6b81;
}
.cart-btn {
    position: relative;
    display: inline-block;
}

.cart-badge {
    position: absolute;
    top: -8px;
    right: -8px;

    background-color: #FF4D4D;
    color: #ffffff;

    font-size: 11px;
    font-weight: 700;

    min-width: 18px;
    height: 18px;
    padding: 0 4px;

    display: flex;
    justify-content: center;
    align-items: center;

    border-radius: 10px;
    border: 2px solid #ffffff;
    box-shadow: 0 2px 5px rgba(0,0,0,0.2);
}
</style>
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
                         style="height: 60px; width: auto; object-fit: contain";                       >
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

            <a href="${pageContext.request.contextPath}/cart" class="icon-btn cart-btn">
                <i class="fa-solid fa-cart-shopping"></i> <span class="cart-badge" id="header-cart-count">
        <c:choose>
            <c:when test="${sessionScope.cart != null}">
                ${sessionScope.cart.totalQuantity()}
            </c:when>
            <c:otherwise>0</c:otherwise>
        </c:choose>
    </span>
            </a>

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
    document.addEventListener('DOMContentLoaded', () => {

        const menuBtn = document.getElementById('menuBtn');
        const dropdownMenu = document.getElementById('dropdownMenu');
        const overlay = document.getElementById('headerOverlay');
        const userToggle = document.getElementById('userToggle');
        const userDropdown = document.getElementById('userDropdown');

        function closeAll() {
            dropdownMenu?.classList.remove('open');
            userDropdown?.classList.remove('open');
            userDropdown?.classList.remove('show');
            overlay?.classList.remove('active');

            if (menuBtn) menuBtn.setAttribute('aria-expanded', 'false');
        }
        if (menuBtn) {
            menuBtn.addEventListener('click', (e) => {
                e.preventDefault(); e.stopPropagation();
                const isOpen = dropdownMenu.classList.toggle('open');
                overlay?.classList.toggle('active', isOpen);
                userDropdown?.classList.remove('open');
                userDropdown?.classList.remove('show');
            });
        }
        if (userToggle) {
            userToggle.addEventListener('click', (e) => {
                e.preventDefault(); // Chặn thẻ a hoặc div click mặc định
                e.stopPropagation(); // Không cho sự kiện lan ra ngoài

                const isOpen = userDropdown.classList.toggle('open');
                userDropdown.classList.toggle('show');
                overlay?.classList.toggle('active', isOpen);

                dropdownMenu?.classList.remove('open');
            });
        }
        document.addEventListener('click', (e) => {
            // Nếu click không trúng menu hay nút toggle thì đóng hết
            if (!dropdownMenu?.contains(e.target) &&
                !menuBtn?.contains(e.target) &&
                !userDropdown?.contains(e.target) &&
                !userToggle?.contains(e.target)) {
                closeAll();
            }
        });

        document.querySelectorAll('.dropdown-menu a').forEach(link => {
            link.addEventListener('click', () => closeAll());
        });

        document.querySelectorAll('.user-dropdown a').forEach(link => {
            link.addEventListener('click', (e) => {
                e.stopPropagation();
                closeAll();


                console.log("Navigating to: " + link.getAttribute('href'));
            });
        });

    });

    function updateHeaderCartCount(newCount) {
        const badge = document.getElementById('header-cart-count');
        if (badge) {
            badge.innerText = newCount;

            // Hiệu ứng nháy nhẹ để người dùng chú ý
            badge.style.transform = "scale(1.2)";
            setTimeout(() => {
                badge.style.transform = "scale(1)";
            }, 200);
        }
    }
</script>

<c:if test="${enableHeaderOverlay}">
    <div id="headerOverlay" class="overlay"></div>
</c:if>

<script src="${pageContext.request.contextPath}/assets/js/SearchAutocomplete.js"></script>

