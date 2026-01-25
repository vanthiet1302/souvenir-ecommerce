<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${data.product.name}</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/Base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ProductDetail.css">

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/common.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/ProductDetail.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/review.js"></script>
</head>
<body>

<jsp:include page="/views/common/header.jsp"/>

<input type="hidden" id="productId" value="${data.product.id}">
<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">

<div class="main-container">

    <!-- ================= TOP ================= -->
    <section class="product-top">

        <div class="product-image">
            <img src="${pageContext.request.contextPath}${data.product.image}">
            <button class="btn-zoom">🔍</button>
        </div>

        <div class="product-info">

            <h1 class="product-name">${data.product.name}</h1>

            <div class="product-meta">
                <span class="rating">⭐ ${data.avgRating}</span>
                <span class="divider">|</span>
                <span class="sold">Đã bán ${data.product.totalSold}</span>
                <span class="divider">|</span>
                <span class="stock">Còn ${data.product.stockQuantity} sản phẩm</span>
            </div>

            <div class="product-price">
                <c:choose>
                    <c:when test="${data.promotion != null}">
                        <span class="old-price">
                            <fmt:formatNumber value="${data.product.price}"/> ₫
                        </span>
                        <span class="sale-price">
                            <fmt:formatNumber value="${data.discountedPrice}"/> ₫
                        </span>
                        <span class="discount">
                            -${data.promotion.discountPercent}%
                        </span>
                    </c:when>
                    <c:otherwise>
                        <span class="normal-price">
                            <fmt:formatNumber value="${data.product.price}"/> ₫
                        </span>
                    </c:otherwise>
                </c:choose>
            </div>

            <p class="short-description">${data.product.shortDescription}</p>

            <form action="${pageContext.request.contextPath}/cart/add" method="post" class="buy-form">
                <input type="hidden" name="productId" value="${data.product.id}">

                <div class="quantity-box">
                    <button type="button" class="qty-btn minus">−</button>
                    <input type="number" name="quantity" value="1" min="1"
                           max="${data.product.stockQuantity}">
                    <button type="button" class="qty-btn plus">+</button>
                </div>

                <div class="action-buttons">
                    <button type="submit" class="btn-add-cart">🛒 Thêm vào giỏ hàng</button>
                    <button type="submit"
                            formaction="${pageContext.request.contextPath}/checkout"
                            class="btn-buy-now">
                        MUA NGAY
                    </button>
                </div>
            </form>

        </div>
    </section>

    <div class="divider"></div>

    <!-- ================= DETAIL ================= -->
    <section class="product-detail-tabs">

        <div class="tab-section">
            <h2>Thông tin chi tiết sản phẩm</h2>
            <div class="description-content">
                ${data.product.description}
            </div>
            <button class="btn-toggle-desc">Xem thêm</button>
        </div>

        <div class="tab-section">
            <h2>Thông số sản phẩm</h2>

            <c:if test="${not empty data.specifications}">
                <table class="spec-table">
                    <c:forEach var="spec" items="${data.specifications}">
                        <tr>
                            <td class="spec-name">${spec.specName}</td>
                            <td>${spec.specValue}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>

            <c:if test="${empty data.specifications}">
                <p class="text-muted">Chưa có thông tin.</p>
            </c:if>
        </div>

    </section>

    <div class="divider"></div>

    <!-- ================= REVIEWS ================= -->
    <section class="product-reviews">
        <h2>Đánh giá sản phẩm</h2>

        <div class="review-summary-box">

            <div class="summary-left">
                <div class="avg-score">${data.avgRating}<span>/5</span></div>
                <div class="total-review">${data.totalReviews} đánh giá</div>
            </div>

            <div class="summary-right">
                <c:forEach begin="1" end="5" var="i">
                    <c:set var="star" value="${6 - i}" />
                    <c:set var="count" value="${data.ratingCount[star]}" />
                    <c:set var="percent"
                           value="${data.totalReviews > 0
                           ? (count * 100 / data.totalReviews)
                           : 0}" />

                    <div class="rating-row">
                        <span>${star} ⭐</span>
                        <div class="progress-bar">
                            <div class="progress-fill" style="width:${percent}%"></div>
                        </div>
                        <span>${percent}%</span>
                    </div>
                </c:forEach>
            </div>

        </div>

        <div class="review-filter">
            <button class="filter-btn active" data-rating="">Tất cả</button>
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

    <div class="divider"></div>

    <!-- ================= RELATED ================= -->
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

</div>

<jsp:include page="/views/common/footer.jsp"/>

</body>
</html>
