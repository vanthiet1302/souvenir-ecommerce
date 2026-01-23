<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${empty reviews}">
    <p class="no-review">Chưa có đánh giá nào cho sản phẩm này.</p>
</c:if>

<c:forEach items="${reviews}" var="r">
    <div class="review-item">
        <div class="review-header">
            <strong>Người dùng</strong>
            <span class="rating">⭐ ${r.rating}/5</span>
            <span class="review-date">
                <fmt:formatDate value="${r.createdAt}" pattern="dd/MM/yyyy"/>
            </span>
        </div>
        <p class="review-content">${r.comment}</p>
    </div>
</c:forEach>
