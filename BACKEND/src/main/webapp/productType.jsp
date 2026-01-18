<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>${category.name}</title>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="page-container product-type-page">

    <!-- FILTER BAR -->
    <section class="filter-bar">
        <span>Sắp xếp theo:</span>

        <a href="category?id=${category.id}&sort=newest"
           class="${sort.name() == 'NEWEST' ? 'active' : ''}">
            Mới nhất
        </a>

        <a href="category?id=${category.id}&sort=price_asc"
           class="${sort.name() == 'PRICE_ASC' ? 'active' : ''}">
            Giá tăng dần
        </a>

        <a href="category?id=${category.id}&sort=price_desc"
           class="${sort.name() == 'PRICE_DESC' ? 'active' : ''}">
            Giá giảm dần
        </a>

        <a href="category?id=${category.id}&sort=popular"
           class="${sort.name() == 'POPULAR' ? 'active' : ''}">
            Bán chạy
        </a>
    </section>

    <!-- PRODUCT LIST -->
    <section class="product-section">
        <h2>${category.name}</h2>

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
            <a href="category?id=${category.id}&page=${i}&sort=${sort}"
               class="${i == currentPage ? 'active' : ''}">
                    ${i}
            </a>
        </c:forEach>
    </div>

    <!-- RANDOM RELATED PRODUCTS -->
    <section class="product-section related-products">
        <h2>CÓ THỂ BẠN QUAN TÂM</h2>

        <div class="product-list">
            <c:forEach var="p" items="${randomRelated}">
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
                    </a>
                </div>
            </c:forEach>
        </div>
    </section>

</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>
