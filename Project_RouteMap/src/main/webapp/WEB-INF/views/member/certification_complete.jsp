<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
<title>회원 가입 인증 완료</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/join_member_complete_page.css">
</head>
<body>
	<!-- 컨테이너 시작 -->
	<div class="wrap"
		style="position: relative; z-index: 1; min-height: 700px; display: flex; align-items: center; justify-content: center">
		<div class="container-bg" style="margin: 0px; position: relative; z-index: 1; border-radius: 20px; width: 50%; height: 400px; display: flex; flex-direction: column; justify-content: center; align-items: center;">
			<!-- 중간 내용 -->
			<h4 style="margin-bottom: 20px; color: #28a745">인증이 완료되었습니다</h4>
			<p style="font-size: 15px; text-align: center">
				인증해주셔서 감사합니다<br>
				로그인 후 이용해 주시기 바랍니다<br> 
				<span id="second">5</span>초 후 로그인 페이지로 이동합니다
			</p>
			<a href="${pageContext.request.contextPath}/member/login_page.do">로그인
				페이지로</a>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/resources/js/member/certification_complete.js"></script>
</body>