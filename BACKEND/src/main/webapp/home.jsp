
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="views/common/header.jsp"/>
<div class="page-container">
<!-- Banner slideshow  -->
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

        <!-- NAV BUTTON -->
        <button class="prev">&#10094;</button>
        <button class="next">&#10095;</button>

        <!-- DOTS -->
        <div class="dots">
            <c:forEach var="item" items="${data.bannerCategories}" varStatus="status">
                <span class="dot" data-slide="${status.index}"></span>
            </c:forEach>
        </div>

    </div>
<!-- Caterory top section   -->
<c:forEach var="cat" items="${topCategories}">
    <section id="Loai${cat.id}" class="product-section">

        <h2>
            <a href="productType?cid=${cat.id}">
                    ${cat.name}
            </a>
        </h2>

        <div class="product-list">
            <c:forEach var="p" items="${cat.products}">
                <div class="product-card">
                    <a href="productDetail?id=${p.id}">
                        <div class="img-box">
                            <img src="${p.imageUrl}" class="product-img"/>
                        </div>

                        <p class="product-name">${p.name}</p>

                        <div class="price-container">
                            <span class="product-price">
                                <fmt:formatNumber value="${p.originalPrice}" type="number" groupingUsed="true"/> ₫
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

        <a class="see-more-btn" href="productType?cid=${cat.id}">
            Xem thêm
        </a>
    </section>
</c:forEach>
<!-- Extension section -->
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

            <!-- WRAPPER (che overflow) -->
            <div class="product-slider-wrapper">

                <!-- SLIDER -->
                <div class="product-slider" id="extSlider">

                    <!-- CATEGORY BÌNH THƯỜNG -->
                    <c:forEach var="cat" items="${extraCategories}">
                        <div class="product-card category-card">
                            <a href="${pageContext.request.contextPath}/category?id=${cat.id}">
                                <img src="${pageContext.request.contextPath}/assets/images/banner/${cat.image}">
                                <p>${cat.name}</p>
                            </a>
                        </div>
                    </c:forEach>

                    <!-- LOGIC CATEGORY: TOP RATED -->
                    <div class="product-card category-card">
                        <a href="${pageContext.request.contextPath}/category?type=topRated">
                            <img src="${pageContext.request.contextPath}/assets/images/banner/top-rated.jpg">
                            <p>Sản phẩm đánh giá tốt</p>
                        </a>
                    </div>

                    <!-- LOGIC CATEGORY: NEW -->
                    <div class="product-card category-card">
                        <a href="${pageContext.request.contextPath}/category?type=new">
                            <img src="${pageContext.request.contextPath}/assets/images/banner/new.jpg">
                            <p>Sản phẩm mới</p>
                        </a>
                    </div>

                </div>
            </div>

            <!-- NEXT -->
            <button class="slider-btn next" id="extNext">›</button>

        </div>
    </section>
<!-- Top Selling  -->
<section class="product-section top-selling">
    <h2>MỌI NGƯỜI ĐANG MUA GÌ</h2>

    <div class="product-list">
        <c:forEach var="p" items="${topSellingProducts}">
            <div class="product-card">
                <a href="productDetail?id=${p.id}">
                    <div class="img-box">
                        <img src="${p.imageUrl}" class="product-img"/>
                    </div>

                    <p class="product-name">${p.name}</p>

                    <div class="price-container">
                        <span class="product-price">
                            <fmt:formatNumber value="${p.originalPrice}" type="number" groupingUsed="true"/> ₫
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
</section>
</div>
<jsp:include page="/views/common/footer.jsp"/>
</body>
</html>
