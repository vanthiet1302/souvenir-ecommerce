<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${data.product.name}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ProductDetail.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>

<jsp:include page="/views/common/header.jsp"/>

<input type="hidden" id="productId" value="${data.product.id}"/>

<!-- BREADCRUMB -->
<div class="breadcrumb">
    <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
    <span>›</span>
    <span>${data.category.name}</span>
    <span>›</span>
    <span>${data.product.name}</span>
</div>

<!-- PRODUCT TOP -->
<section class="product-top">
    <img src="${pageContext.request.contextPath}/assets/images/products/${data.product.image}"
         alt="${data.product.name}">

    <h1>${data.product.name}</h1>

    <c:choose>
        <c:when test="${data.promotion != null}">
            <span class="old-price">
                <fmt:formatNumber value="${data.product.price}" type="number"/>₫
            </span>
            <span class="sale-price">
                <fmt:formatNumber
                        value="${data.product.price * (100 - data.promotion.discountPercent) / 100}"
                        type="number"/>₫
            </span>
        </c:when>
        <c:otherwise>
            <span class="price">
                <fmt:formatNumber value="${data.product.price}" type="number"/>₫
            </span>
        </c:otherwise>
    </c:choose>
</section>

<!-- SPECIFICATIONS -->
<section>
    <h2>Thông số kỹ thuật</h2>
    <c:forEach var="s" items="${data.specifications}">
        <p>${s.specName}: ${s.specValue}</p>
    </c:forEach>
</section>

<!-- REVIEWS -->
<section id="reviews">
    <h2>Đánh giá (${data.totalReviews})</h2>
    <div id="reviewContainer"></div>
</section>

<!-- RELATED -->
<section>
    <h2>Sản phẩm liên quan</h2>
    <c:forEach var="p" items="${data.relatedProducts}">
        <a href="${pageContext.request.contextPath}/product?id=${p.id}">
                ${p.name}
        </a>
    </c:forEach>
</section>

<jsp:include page="/views/common/footer.jsp"/>

<script>
    $(function () {
        $("#reviewContainer").load(
            "${pageContext.request.contextPath}/reviews?productId=${data.product.id}"
        );
    });
</script>

</body>
</html>
