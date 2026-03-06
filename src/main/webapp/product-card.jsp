<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty p}">
    <div class="product-card">
        <a href="${pageContext.request.contextPath}/product?id=${p.id}">

            <!-- IMAGE -->
            <div class="img-box">
                <img src="${pageContext.request.contextPath}${p.image}" alt="${p.name}"/>

                <c:if test="${p.discountPercent != null}">
                    <span class="badge-sale">-${p.discountPercent}%</span>
                </c:if>
            </div>

            <!-- NAME -->
            <p class="product-name">${p.name}</p>

            <!-- PRICE -->
            <div class="price-container">
                <c:choose>
                    <c:when test="${p.discountPercent != null}">
                        <span class="old-price">
                            <fmt:formatNumber value="${p.originalPrice}" groupingUsed="true"/> ₫
                        </span>
                        <span class="current-price">
                            <fmt:formatNumber value="${p.discountedPrice}" groupingUsed="true"/> ₫
                        </span>
                    </c:when>

                    <c:otherwise>
                        <span class="current-price">
                            <fmt:formatNumber value="${p.originalPrice}" groupingUsed="true"/> ₫
                        </span>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- SOLD & RATING -->
            <div class="product-sold">
                Đã bán ${p.totalSold}
                <span class="rating">
                    ★ ${p.avgRating}
                    <c:if test="${p.reviewCount > 0}">
                        (${p.reviewCount})
                    </c:if>
                </span>
            </div>

        </a>
    </div>
</c:if>
