<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>${category.name}</title>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="page-container product-type-layout">

    <!-- LEFT FILTER -->
    <aside class="filter-sidebar">
        <h3>Bộ lọc</h3>

        <form method="get" action="category">
            <input type="hidden" name="id" value="${category.id}"/>

            <div class="filter-group">
                <label>Giá từ</label>
                <input type="number" name="minPrice" value="${minPrice}"/>
            </div>

            <div class="filter-group">
                <label>Đến</label>
                <input type="number" name="maxPrice" value="${maxPrice}"/>
            </div>

            <div class="filter-group">
                <label>Sắp xếp</label>
                <select name="sort">
                    <option value="popular">Bán chạy</option>
                    <option value="newest">Mới nhất</option>
                    <option value="price_asc">Giá tăng</option>
                    <option value="price_desc">Giá giảm</option>
                </select>
            </div>

            <button type="submit">Áp dụng</button>
        </form>
    </aside>

    <!-- RIGHT CONTENT -->
    <main class="product-type-content">

        <!-- BANNER -->
        <div class="category-banner">
            <img src="${pageContext.request.contextPath}/assets/img/banner/category.jpg">
        </div>

        <!-- CATEGORY HEADER -->
        <div class="category-header">
            <h2>${category.name}</h2>
            <span>${totalProducts} sản phẩm</span>
        </div>

        <!-- PRODUCT LIST  -->
        <section class="product-section">
            <div class="product-list">

                <c:forEach var="p" items="${products}">
                    <div class="product-card">
                        <a href="productDetail?id=${p.id}">
                            <div class="img-box">
                                <img src="${p.imageUrl}" class="product-img"/>
                            </div>

                            <p class="product-name">${p.name}</p>

                            <div class="price-container">
                                <span class="product-price">
                                    <fmt:formatNumber value="${p.originalPrice}"
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
        </section>

        <!-- PAGINATION -->
        <div class="pagination">
            <c:forEach begin="1" end="${totalPages}" var="i">
                <a href="category?id=${category.id}&page=${i}"
                   class="${i == currentPage ? 'active' : ''}">
                        ${i}
                </a>
            </c:forEach>
        </div>

    </main>

</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>
