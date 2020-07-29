<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<head>
<title>비밀번호 변경 성공</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/update_password_complete.css">
</head>
<body>
   <!-- 컨테이너 시작 -->
   <div class="wrap" style="position: relative;z-index:1; min-height:700px; display:flex; justify-content:center; align-items:center;">
	   <div class="container-bg" style=" position: relative;z-index:1; border-radius:20px; width:50%; height:400px; display:flex; justify-content:center; align-items:center;">
	      <div class="row">
	         <div class="col" style="text-align:center">
	           <!-- 중간 내용 -->
	         	<h4 style="color:#28a745"><strong>비밀번호 변경에 성공하셨습니다</strong></h4> <br>
	         	 <span id="second">5</span>초 후 로그인 페이지로 이동합니다
	         	 <br>
	          	<a href="${pageContext.request.contextPath}/member/login_page.do">로그인 페이지로</a>
			 </div>
	      </div>
	   </div>
   </div>
<script src="${pageContext.request.contextPath}/resources/js/member/join_member_complete_page.js"></script>
</body>