<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${product.name}</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ProductDetail.css">

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/ProductDetail.js"></script>
</head>
<body>

<!-- ===== productId cho JS ===== -->
<input type="hidden" id="productId" value="${product.id}"/>

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
        <button class="btn-zoom">🔍</button>
    </div>

    <!-- Info -->
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

        <!-- Short description -->
        <p class="short-description">
            ${product.shortDescription}
        </p>

        <!-- Add to cart -->
        <form action="${pageContext.request.contextPath}/cart/add" method="post">
            <input type="hidden" name="productId" value="${product.id}">
            <button type="submit" class="btn btn-primary btn-add-cart">
                Thêm vào giỏ hàng
            </button>
        </form>
    </div>
</section>

<!-- ================= PRODUCT DETAIL TABS ================= -->
<div class="product-detail-tabs">

    <!-- ===== DESCRIPTION ===== -->
    <div class="tab-section">
        <h2>Mô tả sản phẩm</h2>
        <div class="tab-content">
            ${product.description}
        </div>
    </div>

    <!-- ===== SPECIFICATIONS ===== -->
    <div class="tab-section">
        <h2>Thông số kỹ thuật</h2>

        <c:choose>
            <c:when test="${not empty specifications}">
                <table class="spec-table">
                    <tbody>
                    <c:forEach var="spec" items="${specifications}">
                        <tr>
                            <td class="spec-name">${spec.specName}</td>
                            <td class="spec-value">${spec.specValue}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>

            <c:otherwise>
                <p class="no-spec">Đang cập nhật thông số kỹ thuật.</p>
            </c:otherwise>
        </c:choose>
    </div>
</div>


<!-- ================= REVIEWS ================= -->
<section class="product-reviews" id="reviews">

    <h2>Đánh giá sản phẩm</h2>

    <!-- ===== Review Summary ===== -->
    <div class="review-summary">

        <div class="review-average">
            <div class="avg-score">${avgRating}</div>
            <div class="avg-stars">⭐⭐⭐⭐⭐</div>
            <div class="total-reviews">
                ${totalReviews} đánh giá
            </div>
        </div>

        <div class="review-breakdown">
            <c:forEach begin="5" end="1" step="-1" var="star">
                <div class="breakdown-row">
                    <span>${star} ⭐</span>
                    <div class="progress-bar">
                        <div class="progress"
                             style="width:${ratingPercent[star]}%">
                        </div>
                    </div>
                    <span>${ratingCount[star]}</span>
                </div>
            </c:forEach>
        </div>

    </div>

    <!-- ===== Filter & Sort ===== -->
    <div class="review-filter">

        <div class="filter-left">
            <button class="filter-btn active" data-rating="">Tất cả</button>
            <button class="filter-btn" data-rating="5">⭐ 5</button>
            <button class="filter-btn" data-rating="4">⭐ 4</button>
            <button class="filter-btn" data-rating="3">⭐ 3</button>
            <button class="filter-btn" data-rating="2">⭐ 2</button>
            <button class="filter-btn" data-rating="1">⭐ 1</button>
        </div>

        <select id="reviewSort" class="review-sort">
            <option value="newest">Mới nhất</option>
            <option value="oldest">Cũ nhất</option>
        </select>
    </div>

    <!-- ===== Review List (AJAX) ===== -->
    <div class="review-list" id="reviewContainer">
        <!-- review-items.jsp -->
    </div>

    <!-- ===== Load more (optional) ===== -->
    <div class="review-load-more">
        <button id="loadMoreReview" class="btn btn-outline">
            Xem thêm đánh giá
        </button>
    </div>

    <!-- ===== Add review ===== -->
    <div class="review-action">
        <button class="btn btn-outline btn-open-review">
            Đánh giá sản phẩm
        </button>
    </div>

</section>

<!-- ================= RELATED PRODUCTS ================= -->
<section class="related-products">
    <h2>Sản phẩm liên quan</h2>

    <div class="product-list">
        <c:forEach items="${relatedProducts}" var="p">
            <div class="product-card">
                <a href="${pageContext.request.contextPath}/productDetail?id=${p.id}">
                    <div class="img-box">
                        <img src="${pageContext.request.contextPath}/assets/images/products/${p.image}"
                             alt="${p.name}">
                    </div>
                    <h3 class="product-name">${p.name}</h3>
                    <div class="price-container">
                        <span class="current-price">
                            <fmt:formatNumber value="${p.price}" type="number"/>₫
                        </span>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>
</section>

</body>
</html>
