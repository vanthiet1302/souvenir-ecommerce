<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang chủ - INOLA</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/HomePage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/HomePageFooter.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/HomePageMain.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/HomePageSlip.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/STT.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/HomePageAlter.css">
</head>
<body>
<div class="page-container">
    <jsp:include page="/views/common/header.jsp"/>
    <main class="main-container">
        <div class="slideshow-container" id="headerSlideshow">
            <c:forEach items="${bannerList}" var="b">
                <div class="slide">
                    <img src="${pageContext.request.contextPath}/${b.imageUrl}" alt="${b.title}" style="width:100%"/>
                </div>
            </c:forEach>
            <c:if test="${empty bannerList}">
                <div class="slide">
                    <img src="${pageContext.request.contextPath}/assets/images/Banner/img1.jpg" style="width:100%"/>
                </div>
            </c:if>
            <button class="prev">&#10094;</button>
            <button class="next">&#10095;</button>
        </div>
        <hr class="divider">

        <!-- TOP CATEGORY SECTIONS -->
        <c:forEach var="cate" items="${topCategories}">
            <section class="product-section" id="category-${cate.id}">
                <h2><a href="${pageContext.request.contextPath}/category?id=${cate.id}">${cate.name}</a></h2>
                <div class="product-list">
                    <c:forEach var="p" items="${cate.products}">
                        <div class="product-card">
                            <a href="${pageContext.request.contextPath}/product?id=${p.id}">
                                <div class="img-box">
                                    <c:if test="${p.discountPercent > 0 and p.salePrice != null}">
                                        <span class="badge-sale">SALE</span>
                                    </c:if>
                                    <img src="${pageContext.request.contextPath}${p.imageUrl}" alt="${p.name}" class="product-img"/>
                                </div>
                                <p class="product-name">${p.name}</p>
                                <div class="price-container">
                                    <c:choose>
                                        <c:when test="${p.discountPercent > 0 and p.salePrice != null}">
                                            <span class="current-price" style="color: #EE4D2D;">
                                                <fmt:formatNumber value="${p.salePrice}" type="number" groupingUsed="true"/> ₫
                                            </span>
                                            <span class="old-price">
                                                <fmt:formatNumber value="${p.originalPrice}" type="number" groupingUsed="true"/> ₫
                                            </span>
                                            <span class="discount-tag">${p.discountPercent}%</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="current-price">
                                                <fmt:formatNumber value="${p.originalPrice}" type="number" groupingUsed="true"/> ₫
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <p class="product-sold" style="display: flex !important; justify-content: space-between !important; align-items: center !important; font-size: 13px !important; color: #666 !important; padding: 8px 12px !important; margin-top: 4px !important; background: transparent !important;">
                                    <span style="color: #666 !important;">Đã bán ${p.totalSold}</span>
                                    <span class="rating" style="display: flex !important; align-items: center !important; gap: 3px !important; padding: 3px 8px !important; border: 1px solid #e0e0e0 !important; border-radius: 4px !important; font-size: 13px !important; font-weight: 500 !important; background: #fff !important; color: #333 !important;">★ ${p.avgRating}</span>
                                </p>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </section>
        </c:forEach>

        <!-- EXTENSION SECTIONS -->
        <c:forEach var="cate" items="${extensionCategories}">
            <section class="product-section horizontal-section" id="category-${cate.id}">
                <h2>${cate.name}</h2>
                <div class="product-list">
                    <c:forEach var="p" items="${cate.products}">
                        <div class="product-card">
                            <a href="${pageContext.request.contextPath}/product?id=${p.id}">
                                <div class="img-box">
                                    <c:if test="${p.discountPercent > 0 and p.salePrice != null}">
                                        <span class="badge-sale">SALE</span>
                                    </c:if>
                                    <img src="${pageContext.request.contextPath}${p.imageUrl}" alt="${p.name}" class="product-img"/>
                                </div>
                                <p class="product-name">${p.name}</p>
                                <div class="price-container">
                                    <c:choose>
                                        <c:when test="${p.discountPercent > 0 and p.salePrice != null}">
                                            <span class="current-price" style="color: #EE4D2D;">
                                                <fmt:formatNumber value="${p.salePrice}" type="number" groupingUsed="true"/> ₫
                                            </span>
                                            <span class="old-price">
                                                <fmt:formatNumber value="${p.originalPrice}" type="number" groupingUsed="true"/> ₫
                                            </span>
                                            <span class="discount-tag">${p.discountPercent}%</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="current-price">
                                                <fmt:formatNumber value="${p.originalPrice}" type="number" groupingUsed="true"/> ₫
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <p class="product-sold" style="display: flex !important; justify-content: space-between !important; align-items: center !important; font-size: 13px !important; color: #666 !important; padding: 8px 12px !important; margin-top: 4px !important; background: transparent !important;">
                                    <span style="color: #666 !important;">Đã bán ${p.totalSold}</span>
                                    <span class="rating" style="display: flex !important; align-items: center !important; gap: 3px !important; padding: 3px 8px !important; border: 1px solid #e0e0e0 !important; border-radius: 4px !important; font-size: 13px !important; font-weight: 500 !important; background: #fff !important; color: #333 !important;">★ ${p.avgRating}</span>
                                </p>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </section>
        </c:forEach>
    </main>
    <jsp:include page="/views/common/footer.jsp"/>
    <a href="#"><button id="scrollToTopBtn"><i class="fas fa-chevron-up"></i></button></a>
</div>
<script src="${pageContext.request.contextPath}/assets/js/HeaderAfter.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/HomePage.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/Header.js"></script>
</body>
</html>
