<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang chủ</title>

    <!-- CSS -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/HomePage.css">
</head>
<body>

<jsp:include page="/views/common/header.jsp"/>

<div class="page-container">

    <!-- ================= BANNER SLIDESHOW ================= -->
    <div class="slideshow-container" id="headerSlideshow">

        <!-- SLIDES -->
        <c:forEach var="item" items="${data.bannerCategories}" varStatus="status">
            <div class="slide">
                <a href="${pageContext.request.contextPath}/category?id=${item.category.id}">
                    <img src="${pageContext.request.contextPath}/assets/images/Banner/${item.category.image}"
                         alt="${item.category.name}">
                </a>
            </div>
        </c:forEach>

        <!-- NAV -->
        <button class="prev">&#10094;</button>
        <button class="next">&#10095;</button>

        <!-- DOTS -->
        <div class="dots">
            <c:forEach var="item" items="${data.bannerCategories}" varStatus="status">
                <span class="dot" data-slide="${status.index}"></span>
            </c:forEach>
        </div>
    </div>

    <!-- ================= TOP CATEGORY SECTIONS ================= -->
    <c:forEach var="section" items="${data.topCategorySections}">
        <section id="Loai${section.category.id}" class="product-section">

            <h2>
                <a href="${pageContext.request.contextPath}/category?id=${section.category.id}">
                        ${section.category.name}
                </a>
            </h2>

            <div class="product-list">
                <c:forEach var="p" items="${section.products}">
                    <div class="product-card">
                        <a href="${pageContext.request.contextPath}/product?id=${p.id}">

                            <div class="img-box">
                                <img src="${pageContext.request.contextPath}/assets/images/products/${p.image}"
                                     alt="${p.name}">
                            </div>

                            <p class="product-name">${p.name}</p>

                            <div class="price-container">
                                <span class="product-price">
                                    <fmt:formatNumber value="${p.price}"
                                                      type="number"
                                                      groupingUsed="true"/> ₫
                                </span>
                            </div>

                            <p class="product-sold">
                                Đã bán ${p.totalSold}
                                <span class="rating">★ ${p.avgRating}</span>
                            </p>
                        </a>
                    </div>
                </c:forEach>
            </div>

            <a class="see-more-btn"
               href="${pageContext.request.contextPath}/category?id=${section.category.id}">
                Xem thêm
            </a>
        </section>
    </c:forEach>

    <!-- ================= EXTENSION SECTION ================= -->
    <section id="extension" class="product-section horizontal-section">

        <!-- LEFT -->
        <div class="left-content">
            <h2>DANH MỤC KHÁC</h2>
            <a class="see-more-btn"
               href="${pageContext.request.contextPath}/category">
                Xem thêm
            </a>
        </div>

        <!-- RIGHT -->
        <div class="right-content">

            <!-- PREV -->
            <button class="slider-btn prev" id="extPrev">‹</button>

            <!-- WRAPPER -->
            <div class="product-slider-wrapper">
                <div class="product-slider" id="extSlider">

                    <!-- CATEGORY FROM EXTENSION -->
                    <c:forEach var="section" items="${data.extensionSections}">
                        <div class="product-card category-card">
                            <a href="${pageContext.request.contextPath}/category?id=${section.category.id}">
                                <img src="${pageContext.request.contextPath}/assets/images/Banner/${section.category.image}"
                                     alt="${section.category.name}">
                                <p>${section.category.name}</p>
                            </a>
                        </div>
                    </c:forEach>

                    <!-- LOGIC CATEGORY -->
                    <div class="product-card category-card">
                        <a href="${pageContext.request.contextPath}/category?type=topRated">
                            <img src="${pageContext.request.contextPath}/assets/images/Banner/top-rated.jpg">
                            <p>Sản phẩm đánh giá tốt</p>
                        </a>
                    </div>

                    <div class="product-card category-card">
                        <a href="${pageContext.request.contextPath}/category?type=new">
                            <img src="${pageContext.request.contextPath}/assets/images/Banner/new.jpg">
                            <p>Sản phẩm mới</p>
                        </a>
                    </div>

                </div>
            </div>

            <!-- NEXT -->
            <button class="slider-btn next" id="extNext">›</button>
        </div>
    </section>

</div>

<jsp:include page="/views/common/footer.jsp"/>
<!-- JS -->
<script src="${pageContext.request.contextPath}/assets/js/HomePage.js"></script>
</body>
</html>
