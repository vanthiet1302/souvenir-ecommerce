<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang chủ - INOLA</title>

    <!-- ICON -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <!-- CSS HOME -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/HomePage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/HomePageFooter.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/HomePageMain.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/HomePageSlip.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/STT.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/HomePageAlter.css">
</head>

<body>
<div class="page-container">

    <!-- HEADER -->
    <jsp:include page="/views/common/header.jsp"/>

    <!-- MAIN CONTENT -->
    <main class="main-container">

        <!-- SLIDESHOW -->
        <div class="slideshow-container" id="headerSlideshow">
            <c:forEach items="${bannerList}" var="b">
                <div class="slide">
                    <img src="${pageContext.request.contextPath}/${b.imageUrl}"
                         alt="${b.title}" style="width:100%"/>
                </div>
            </c:forEach>

            <c:if test="${empty bannerList}">
                <div class="slide">
                    <img src="${pageContext.request.contextPath}/assets/images/Banner/img1.jpg"
                         style="width:100%"/>
                </div>
            </c:if>

            <button class="prev">&#10094;</button>
            <button class="next">&#10095;</button>
        </div>

        <hr class="divider">

        <!-- ================= TOP CATEGORY SECTIONS ================= -->
        <c:forEach var="cate" items="${topCategories}">
            <section class="product-section">
                <h2>
                    <a href="${pageContext.request.contextPath}/category?id=${cate.id}">
                            ${cate.name}
                    </a>
                </h2>

                <div class="product-list">
                    <c:forEach var="p" items="${cate.products}">
                        <div class="product-card">

                            <a href="${pageContext.request.contextPath}/product?id=${p.id}">
                                <div class="img-box">
                                    <img src="${pageContext.request.contextPath}${p.imageUrl}"
                                         alt="${p.name}"
                                         class="product-img"/>
                                </div>

                                <p class="product-name">${p.name}</p>

                                <div class="price-container">
                                    <span class="current-price">
                                        ${p.originalPrice} ₫
                                    </span>
                                </div>
                            </a>

                            <a href="${pageContext.request.contextPath}/add-cart?productId=${p.id}&quantity=1"
                               class="see-more-btn">
                                Thêm vào giỏ
                            </a>

                        </div>
                    </c:forEach>
                </div>
            </section>
        </c:forEach>

        <!-- ================= EXTENSION SECTIONS ================= -->
        <c:forEach var="cate" items="${extensionCategories}">
            <section class="product-section horizontal-section">
                <h2>${cate.name}</h2>

                <div class="product-list">
                    <c:forEach var="p" items="${cate.products}">
                        <div class="product-card">

                            <a href="${pageContext.request.contextPath}/product?id=${p.id}">
                                <div class="img-box">
                                    <img src="${pageContext.request.contextPath}${p.imageUrl}"
                                         alt="${p.name}"
                                         class="product-img"/>
                                </div>

                                <p class="product-name">${p.name}</p>

                                <div class="price-container">
                                    <span class="current-price">
                                        ${p.originalPrice} ₫
                                    </span>
                                </div>
                            </a>

                        </div>
                    </c:forEach>
                </div>
            </section>
        </c:forEach>

    </main>

    <!-- FOOTER -->
    <jsp:include page="/views/common/footer.jsp"/>

    <!-- SCROLL TO TOP -->
    <a href="#">
        <button id="scrollToTopBtn">
            <i class="fas fa-chevron-up"></i>
        </button>
    </a>

</div>

<!-- JS -->
<script src="${pageContext.request.contextPath}/assets/js/HeaderAfter.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/HomePage.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/Header.js"></script>
</body>
</html>
