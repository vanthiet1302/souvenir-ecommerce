<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng của bạn - INOLA</title>

    <meta name="context-path" content="${pageContext.request.contextPath}">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ShoppingCart.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/HomePageFooter.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"/>

    <style>
        .checkout-btn {
            display: block !important; /* Bắt buộc xuống dòng */
            width: 100%;
            margin-top: 30px !important; /* Tạo khoảng cách lớn với dòng tổng tiền */
            padding: 15px 0;
            text-align: center;

            background-color: #4b368f;
            color: #fff !important; /* Màu chữ trắng */
            border-radius: 8px;
            font-weight: 700;
            text-transform: uppercase;
            box-shadow: 0 4px 10px rgba(75, 54, 143, 0.3);
            transition: all 0.3s;
        }

        .checkout-btn:hover {
            background-color: #6a4c9c;
            transform: translateY(-2px);
            text-decoration: none;
        }

        /* Chỉnh lại khoảng cách cho phần tóm tắt */
        .summary-card {
            padding-bottom: 30px; /* Thêm khoảng trống dưới cùng của khung */
        }
    </style>
</head>
<body>

<div class="page-wrapper">
    <div class="cart-layout">
        <div class="cart-items-section">

            <div class="continue-shopping">
                <a href="${pageContext.request.contextPath}/home">
                    <i class="fa-solid fa-arrow-left"></i> Tiếp tục mua sắm
                </a>
            </div>

            <div class="section-header">
                <h2>Giỏ hàng của bạn</h2>
                <span id="cart-total-qty">Tổng cộng: ${cart.totalQuantity()} sản phẩm</span>
            </div>

            <c:if test="${cart.totalQuantity() == 0}">
                <div class="empty-cart" style="text-align: center; padding: 40px 0;">
                    <p style="margin-bottom: 20px; font-size: 16px; color: #666;">Giỏ hàng của bạn đang trống.</p>
                    <a class="checkout-btn" href="${pageContext.request.contextPath}/home" style="width: auto; display: inline-block !important; padding: 10px 30px;">
                        Mua sắm ngay
                    </a>
                </div>
            </c:if>

            <c:forEach items="${cart.items}" var="item">
                <div class="cart-item-card" data-product-id="${item.product.id}">

                    <div class="item-image">
                        <img src="${pageContext.request.contextPath}${item.product.imageUrl}"
                             alt="${item.product.name}">
                    </div>

                    <div class="item-details">
                        <h3>${item.product.name}</h3>
                        <p>
                            Đơn giá:
                            <fmt:formatNumber value="${item.price}" groupingUsed="true"/>₫
                        </p>
                    </div>

                    <div class="item-price-quantity">
                        <span class="item-price">
                            <fmt:formatNumber value="${item.subTotal}" groupingUsed="true"/>₫
                        </span>

                        <div class="quantity-selector">
                            <button type="button" class="qty-btn minus-btn">-</button>

                            <input type="number"
                                   class="qty-input"
                                   value="${item.quantity}"
                                   min="1">

                            <button type="button" class="qty-btn plus-btn">+</button>
                        </div>
                    </div>

                    <button type="button"
                            class="remove-item-btn"
                            data-product-id="${item.product.id}">
                        <i class="fa-solid fa-trash-can"></i> Xóa
                    </button>

                </div>
            </c:forEach>

        </div>

        <div class="order-summary-section">
            <div class="summary-card">
                <h2 class="summary-card-title">Tóm tắt đơn hàng</h2>

                <div class="summary-row">
                    <span>Tạm tính</span>
                    <span id="cart-subtotal"><fmt:formatNumber value="${cart.total()}" groupingUsed="true"/>₫</span>
                </div>

                <div class="summary-total-row" style="border-top: none; margin-top: 0; padding-top: 5px; color: #666; font-size: 14px;">
                    <span>Phí vận chuyển</span>
                    <span>Liên hệ sau</span>
                </div>

                <div class="summary-total-row">
                    <span>Tổng thanh toán</span>
                    <span id="cart-total-pay" class="final-total"><fmt:formatNumber value="${cart.total()}" groupingUsed="true"/>₫</span>
                </div>

                <a class="checkout-btn"
                   href="${pageContext.request.contextPath}/checkout"
                   onclick="return confirm('Xác nhận thanh toán?')">
                    XÁC NHẬN THANH TOÁN
                </a>
            </div>
        </div>

    </div>

    <jsp:include page="/views/common/footer.jsp"/>
</div>

<script src="${pageContext.request.contextPath}/assets/js/ShoppingCart.js"></script>
</body>
</html>