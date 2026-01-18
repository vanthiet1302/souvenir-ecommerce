<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${product.name}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/product-detail.css">
</head>
<body>

<!-- ================= Breadcrumb ================= -->
<div class="breadcrumb">
    <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
    <span>›</span>
    <a href="#">${product.categoryName}</a>
    <span>›</span>
    <span>${product.name}</span>
</div>

<!-- ================= TOP SECTION ================= -->
<section class="product-top">
    <!-- Image -->
    <div class="product-image">
        <img src="${pageContext.request.contextPath}/assets/images/products/${product.image}"
             alt="${product.name}">
    </div>

    <!-- Main info -->
    <div class="product-info">
        <h1 class="product-name">${product.name}</h1>

        <!-- Price -->
        <div class="product-price">
            <c:choose>
                <c:when test="${promotion != null}">
                    <span class="old-price">
                        <fmt:formatNumber value="${product.price}" type="number"/>₫
                    </span>
                    <span class="sale-price">
                        <fmt:formatNumber value="${promotion.discountedPrice}" type="number"/>₫
                    </span>
                    <span class="discount">
                        -${promotion.discountPercent}%
                    </span>
                </c:when>
                <c:otherwise>
                    <span class="normal-price">
                        <fmt:formatNumber value="${product.price}" type="number"/>₫
                    </span>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Short info -->
        <p class="short-description">
            ${product.shortDescription}
        </p>

        <!-- Add to cart -->
        <form action="${pageContext.request.contextPath}/cart/add" method="post">
            <input type="hidden" name="productId" value="${product.id}">
            <button type="submit" class="btn-add-cart">
                Thêm vào giỏ hàng
            </button>
        </form>
    </div>
</section>

<!-- ================= DESCRIPTION + DETAIL ================= -->
<section class="product-description">
    <h2>Thông tin chi tiết sản phẩm</h2>
    <div class="description-content">
        ${product.description}
    </div>
</section>

<!-- ================= REVIEWS ================= -->
<section class="product-reviews">
    <h2>Đánh giá sản phẩm</h2>

    <c:if test="${empty reviews}">
        <p class="no-review">Chưa có đánh giá nào cho sản phẩm này.</p>
    </c:if>

    <c:forEach items="${reviews}" var="r">
        <div class="review-item">
            <div class="review-header">
                <strong>${r.userName}</strong>
                <span class="rating">⭐ ${r.rating}/5</span>
            </div>
            <p class="review-content">${r.comment}</p>
            <span class="review-date">
                <fmt:formatDate value="${r.createdAt}" pattern="dd/MM/yyyy"/>
            </span>
        </div>
    </c:forEach>
</section>
<!-- ================= RELATED PRODUCTS ================= -->
<section class="related-products">
    <h2>Sản phẩm liên quan</h2>

    <div class="product-list">
        <c:forEach items="${relatedProducts}" var="p">
            <div class="product-card">
                <a href="${pageContext.request.contextPath}/productDetail?id=${p.id}">
                    <img src="${pageContext.request.contextPath}/assets/images/products/${p.image}"
                         alt="${p.name}">
                    <h3>${p.name}</h3>
                    <span>
                        <fmt:formatNumber value="${p.price}" type="number"/>₫
                    </span>
                </a>
            </div>
        </c:forEach>
    </div>
</section>
</body>
</html>
