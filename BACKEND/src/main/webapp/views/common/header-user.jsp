<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="header-wrapper">
    <header class="header-container page-container">

        <div class="main-header">

            <!-- LEFT : LOGO -->
            <div class="left">
                <div class="logo">
                    <a href="${pageContext.request.contextPath}/home">
                        <img src="${pageContext.request.contextPath}/assets/images/Logo/Logo-removebg-preview.png"
                             alt="INOLA Logo">
                    </a>
                </div>
            </div>

            <!-- CENTER : EMPTY (giữ layout) -->
            <div class="center"></div>

            <!-- RIGHT : CART -->
            <div class="right">
                <a href="${pageContext.request.contextPath}/cart" class="cart">
                    <i class="fa fa-shopping-cart"></i>
                    <c:if test="${cartItemCount > 0}">
                        <span class="cart-count">${cartItemCount}</span>
                    </c:if>
                </a>
            </div>

        </div>

    </header>
</div>
