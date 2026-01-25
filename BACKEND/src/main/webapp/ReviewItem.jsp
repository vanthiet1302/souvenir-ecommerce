<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${empty reviews}">
    <p class="text-muted">Chưa có đánh giá nào cho sản phẩm này.</p>
</c:if>

<c:forEach var="r" items="${reviews}">
    <div class="review-item">

        <div class="review-avatar">
                ${fn:substring(r.userName, 0, 1)}
        </div>

        <div class="review-body">
            <div class="review-header">
                <strong>${r.userName}</strong>
                <span class="review-date">
                    <fmt:formatDate value="${r.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                </span>
            </div>

            <div class="review-stars">
                <c:forEach begin="1" end="${r.rating}">⭐</c:forEach>
            </div>

            <p class="review-text">${r.comment}</p>

            <c:if test="${not empty r.images}">
                <div class="review-images">
                    <c:forEach var="img" items="${r.images}">
                        <img src="${pageContext.request.contextPath}${img}">
                    </c:forEach>
                </div>
            </c:if>
        </div>

    </div>
</c:forEach>
