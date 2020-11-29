<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
response.setDateHeader("Expires", 0L); // Do not cache in proxy server
%>
<!DOCTYPE html>
<html>
<head>
<base href="/" /> 
<!-- 부트스트랩  CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<!-- CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/member/login_page.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/font.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/common.css">
<!-- Font Awesome -->
<script src="https://kit.fontawesome.com/140e7037bd.js" crossorigin="anonymous"></script>
<!-- favicon -->
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon.ico">
<!-- font -->
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
<meta charset="UTF-8">
<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<title>데이사이드 휴가 신청</title>
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="container">
	<div class="login-box">
		<div class="title-box">
			<div class="logo">
				<img src="${pageContext.request.contextPath}/resources/img/dayside-logo.png">
			</div>
			<div class="logo"><h3>휴가 신청</h3></div>
		</div>
		<form id="frm">
			<div class="form-content">
				<label for="emailId">이메일 아이디</label>
			    <input type="email" class="form-control" id="emailId" name="emailId" placeholder="이메일 아이디"
			    	onfocus="this.placeholder=''" onblur="this.placeholder='이메일 아이디'">
			    <div class="id-blank">아이디를 입력해주세요.</div>
			</div>
			<div class="form-content">
			    <label for="pw">비밀번호</label>
			    <input type="password" class="form-control" id="pw" name="pw" placeholder="비밀번호"
			    	onfocus="this.placeholder=''" onblur="this.placeholder='비밀번호'">
			    <div class="pw-blank">비밀번호를 입력해주세요.</div>
			</div>
			<div class="form-content">
			    <div class="info-check alert alert-danger" role="alert"></div>
			</div>
			<div class="form-content btns">
			    <input type="button" id="loginBtn" class="btn btn-warning" value="로그인">
			    <a class="a-box" href="/member/join">
			    	<button type="button" class="btn btn-outline-secondary">회원가입</button>
			    </a>
			</div>
		</form>
	</div>
</div>
<!-- Popper -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" 
	integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<!-- Bootstrap JS-->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" 
	integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
<jsp:include page="../common/footer.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/resources/js/member/login_page.js"></script>
</body>
</html>