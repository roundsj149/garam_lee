<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>

<!DOCTYPE html>
<html>
<head>
<!-- 부트스트랩 css -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<!-- footer css -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/footer.css">

<!-- font관련 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/font-style.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/global_nav.css">

<!-- 공통 css(버튼 등) -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/common.css">

<!-- jquery 3.5.1 -->
<script src="https://code.jquery.com/jquery-3.5.1.js"
	integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
	crossorigin="anonymous"></script>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title >
	<decorator:title default="asdf"></decorator:title>
</title>
<decorator:head></decorator:head>
</head>
<body>
<!-- header include -->
<jsp:include page="../global_nav.jsp"></jsp:include>
<decorator:body></decorator:body>

<!-- footer include -->
<jsp:include page="../footer.jsp"></jsp:include>

<!-- Popper.js - jquery 보다 아래에 배치 -->
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
<!-- 부트스트랩 4.41 js - jquery 보다 아래에 배치 -->
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>
<!-- 폰트어썸 -->
<script src="https://kit.fontawesome.com/140e7037bd.js"
	crossorigin="anonymous"></script>
</body>
</html>