<c:if test="${empty reviews}">
    <p class="no-review">Chưa có đánh giá nào cho sản phẩm này.</p>
</c:if>

<c:forEach items="${reviews}" var="r">
    <div class="review-item">

        <div class="review-header">
            <div>
                <strong>Người dùng</strong>
                <span class="rating">⭐ ${r.rating}/5</span>
            </div>
            <span class="review-date">
                <fmt:formatDate value="${r.createdAt}" pattern="dd/MM/yyyy"/>
            </span>
        </div>

        <p class="review-content">${r.comment}</p>

    </div>
</c:forEach>
