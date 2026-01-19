<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty reviewList}">
    <p class="no-review">Chưa có đánh giá nào cho sản phẩm này.</p>
</c:if>

<c:forEach items="${reviewList}" var="r">
    <div class="review-item">

        <div class="review-header">
            <div>
                <strong>${r.userName}</strong>
                <span class="rating">⭐ ${r.rating}/5</span>
            </div>
            <span class="review-date">
                <fmt:formatDate value="${r.createdAt}" pattern="dd/MM/yyyy"/>
            </span>
        </div>

        <p class="review-content">
                ${r.comment}
        </p>

    </div>
</c:forEach>
