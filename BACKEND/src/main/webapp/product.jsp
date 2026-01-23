<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${data.product.name}</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/ProductDetail.css">

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/ProductDetail.js"></script>
</head>
<body>

<jsp:include page="/views/common/header.jsp"/>

<input type="hidden" id="productId" value="${data.product.id}">
<input type="hidden" id="contextPath"
       value="${pageContext.request.contextPath}">

<!-- PRODUCT TOP -->
<section class="product-top">

    <div class="product-image">
        <img src="${pageContext.request.contextPath}/assets/images/products/${data.product.image}">
        <button class="btn-zoom">🔍</button>
    </div>

    <div class="product-info">
        <h1>${data.product.name}</h1>

        <div class="product-price">
            <c:choose>
                <c:when test="${data.promotion != null}">
                    <span class="old-price">
                        <fmt:formatNumber value="${data.product.price}"/>₫
                    </span>
                    <span class="sale-price">
                        <fmt:formatNumber value="${data.discountedPrice}"/>₫
                    </span>
                </c:when>
                <c:otherwise>
                    <span class="normal-price">
                        <fmt:formatNumber value="${data.product.price}"/>₫
                    </span>
                </c:otherwise>
            </c:choose>
        </div>

        <p>${data.product.shortDescription}</p>
    </div>
</section>

<!-- REVIEW SUMMARY -->
<section class="product-reviews">
    <h2>Đánh giá sản phẩm</h2>

    <div class="review-summary">
        <div>${data.avgRating} ⭐</div>
        <div>${data.totalReviews} đánh giá</div>

        <c:forEach begin="5" end="1" step="-1" var="star">
            <div>${star} ⭐ : ${data.ratingCount[star]}</div>
        </c:forEach>
    </div>

    <!-- FILTER -->
    <div class="review-filter">
        <button class="filter-btn" data-rating="">Tất cả</button>
        <button class="filter-btn" data-rating="5">5 ⭐</button>
        <button class="filter-btn" data-rating="4">4 ⭐</button>
        <button class="filter-btn" data-rating="3">3 ⭐</button>
        <button class="filter-btn" data-rating="2">2 ⭐</button>
        <button class="filter-btn" data-rating="1">1 ⭐</button>

        <select class="review-sort">
            <option value="newest">Mới nhất</option>
            <option value="oldest">Cũ nhất</option>
        </select>
    </div>

    <div id="reviewContainer"></div>

    <button id="loadMoreReview">Xem thêm</button>
</section>

<!-- RELATED -->
<section class="related-products">
    <h2>Sản phẩm liên quan</h2>
    <div class="product-list">
        <c:forEach var="p" items="${data.relatedProducts}">
            <a href="${pageContext.request.contextPath}/product?id=${p.id}">
                    ${p.name}
            </a>
        </c:forEach>
    </div>
</section>

<jsp:include page="/views/common/footer.jsp"/>

</body>
</html>
