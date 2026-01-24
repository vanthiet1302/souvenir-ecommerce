<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${data.category.name}</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/ProductType.css">
</head>
<body>

<jsp:include page="/views/common/header.jsp"/>

<div class="page-container product-type-layout">

    <!-- ================= LEFT FILTER ================= -->
    <aside class="filter-sidebar">
        <h3>Bộ lọc</h3>

        <form method="get" action="${pageContext.request.contextPath}/category">
            <input type="hidden" name="id" value="${data.category.id}"/>

            <div class="filter-group">
                <label>Giá từ</label>
                <input type="number" name="minPrice" value="${data.minPrice}"/>
            </div>

            <div class="filter-group">
                <label>Đến</label>
                <input type="number" name="maxPrice" value="${data.maxPrice}"/>
            </div>

            <div class="filter-group">
                <label>Sắp xếp</label>
                <select name="sort">
                    <option value="popular"
                    ${data.sort == 'POPULAR' ? 'selected' : ''}>
                        Bán chạy
                    </option>
                    <option value="newest"
                    ${data.sort == 'NEWEST' ? 'selected' : ''}>
                        Mới nhất
                    </option>
                    <option value="price_asc"
                    ${data.sort == 'PRICE_ASC' ? 'selected' : ''}>
                        Giá tăng
                    </option>
                    <option value="price_desc"
                    ${data.sort == 'PRICE_DESC' ? 'selected' : ''}>
                        Giá giảm
                    </option>
                </select>
            </div>

            <button type="submit">Áp dụng</button>
        </form>
    </aside>

    <!-- ================= RIGHT CONTENT ================= -->
    <main class="product-type-content">

        <!-- ===== CATEGORY BANNER ===== -->
        <div class="category-banner">
            <img src="${pageContext.request.contextPath}/assets/img/banner/category.jpg"
                 alt="${data.category.name}">
        </div>

        <!-- ===== CATEGORY HEADER ===== -->
        <div class="category-header">
            <h2>${data.category.name}</h2>
            <span>${data.totalProducts} sản phẩm</span>
        </div>

        <!-- ===== PRODUCT LIST ===== -->
        <section class="product-section">
            <div class="product-list">

                <c:forEach var="p" items="${data.products}">
                    <div class="product-card">
                        <a href="${pageContext.request.contextPath}/product?id=${p.id}">

                            <div class="img-box">
                                <img src="${pageContext.request.contextPath}/assets/images/products/${p.image}"
                                     alt="${p.name}"
                                     class="product-img"/>
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

                <c:if test="${empty data.products}">
                    <p class="empty-state">
                        Không có sản phẩm phù hợp với bộ lọc.
                    </p>
                </c:if>

            </div>
        </section>

        <!-- ===== PAGINATION ===== -->
        <div class="pagination">
            <c:forEach begin="1" end="${data.totalPages}" var="i">
                <a href="${pageContext.request.contextPath}/category?id=${data.category.id}&page=${i}&minPrice=${data.minPrice}&maxPrice=${data.maxPrice}&sort=${data.sortParam}"
                   class="${i == data.currentPage ? 'active' : ''}">
                        ${i}
                </a>
            </c:forEach>
        </div>

    </main>
</div>

<jsp:include page="/views/common/footer.jsp"/>

</body>
</html>
