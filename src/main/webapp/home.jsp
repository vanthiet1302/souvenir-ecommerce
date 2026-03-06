<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="page-container">

    <!-- ================= BANNER SLIDESHOW ================= -->
    <div class="slideshow-container" id="headerSlideshow">

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
                <c:forEach var="p" items="${section.productCards}">
                    <c:set var="p" value="${p}" scope="request"/>
                    <jsp:include page="product-card.jsp"/>
                </c:forEach>

                <c:if test="${empty section.productCards}">
                    <p class="empty-state">Chưa có sản phẩm cho danh mục này.</p>
                </c:if>
            </div>

            <a class="see-more-btn"
               href="${pageContext.request.contextPath}/category?id=${section.category.id}">
                Xem thêm
            </a>

        </section>
    </c:forEach>

    <!-- ================= EXTENSION CATEGORY SECTION ================= -->
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

            <button class="slider-btn prev" id="extPrev">‹</button>

            <div class="product-slider-wrapper">
                <div class="product-slider" id="extSlider">

                    <c:forEach var="section" items="${data.extensionSections}">
                        <div class="product-card category-card">
                            <a href="${pageContext.request.contextPath}/category?id=${section.category.id}">
                                <img src="${pageContext.request.contextPath}/assets/images/Banner/${section.category.image}"
                                     alt="${section.category.name}">
                                <p>${section.category.name}</p>
                            </a>
                        </div>
                    </c:forEach>

                </div>
            </div>

            <button class="slider-btn next" id="extNext">›</button>
        </div>
    </section>

    <!-- ================= TOP RATED PRODUCTS ================= -->
    <section class="section related-products">
        <div class="main-container">

            <h2 class="related-title">Sản phẩm đánh giá cao</h2>

            <div class="related-grid">
                <c:forEach var="p" items="${data.topRatedProductCards}">
                    <c:set var="p" value="${p}" scope="request"/>
                    <jsp:include page="product-card.jsp"/>
                </c:forEach>

            </div>

        </div>
    </section>

    <!-- ================= NEWEST PRODUCTS ================= -->
    <section class="section related-products">
        <div class="main-container">

            <h2 class="related-title">Sản phẩm mới</h2>

            <div class="related-grid">
                <c:forEach var="p" items="${data.newestProductCards}">
                    <c:set var="p" value="${p}" scope="request"/>
                    <jsp:include page="product-card.jsp"/>
                </c:forEach>


            </div>

        </div>
    </section>

</div>
