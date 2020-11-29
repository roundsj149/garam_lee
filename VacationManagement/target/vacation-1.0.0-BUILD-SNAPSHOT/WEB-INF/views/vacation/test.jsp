<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<!-- 부트스트랩  CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<!-- CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/font.css">
<!-- Font Awesome -->
<script src="https://kit.fontawesome.com/140e7037bd.js" crossorigin="anonymous"></script>
<!-- favicon -->
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon.ico">
<!-- font -->
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap" rel="stylesheet">
<meta name="viewport" content="width=device-width,initial-scale=1">
<meta charset="UTF-8">
<title>데이사이드 휴가 신청</title>
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="container">
	<input id="testBtn" type="button" value="AJAX TEST">
	<div id="memberList"></div>
</div>
<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<!-- Popper -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" 
	integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<!-- Bootstrap JS-->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" 
	integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
<jsp:include page="../common/footer.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/resources/js/vacation/test.js"></script>
</body>
</html>