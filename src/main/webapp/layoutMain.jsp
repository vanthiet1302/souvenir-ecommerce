<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- ===== TITLE ===== -->
    <title>
        <c:choose>
            <c:when test="${not empty pageTitle}">
                ${pageTitle}
            </c:when>
            <c:otherwise>
                INOLA
            </c:otherwise>
        </c:choose>
    </title>

    <!-- ===== BASE / RESET ===== -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/Base.css">

    <!-- ===== HEADER & FOOTER ===== -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/header.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/footer.css">

    <!-- ===== PAGE-SPECIFIC CSS (OPTIONAL) ===== -->
    <c:if test="${not empty pageCss}">
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/assets/css/${pageCss}">
    </c:if>

    <!-- ===== ICON / FONT ===== -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>

<!-- ================= HEADER ================= -->
<jsp:include page="/views/common/header.jsp"/>

<!-- ================= MAIN CONTENT ================= -->
<main id="main-content">
    <!-- contentPage sẽ là Home.jsp / Product.jsp / ProductType.jsp -->
    <jsp:include page="${contentPage}"/>
</main>

<!-- ================= FOOTER ================= -->

<jsp:include page="/views/common/footer.jsp"/>

<!-- ================= COMMON JS ================= -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
        integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/assets/js/common.js"></script>

<!-- ================= PAGE-SPECIFIC JS (OPTIONAL) ================= -->
<c:if test="${not empty pageJs}">
    <script src="${pageContext.request.contextPath}/assets/js/${pageJs}"></script>
</c:if>

</body>
</html>
