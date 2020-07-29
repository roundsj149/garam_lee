<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<%
response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
response.setDateHeader("Expires", 0L); // Do not cache in proxy server
%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<head>
<title>마이페이지</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/mypage.css">
</head>
<body>
<!-- container 시작 -->
	<div class="container" style="min-height:700px">
		<!-- 마이페이지 시작 -->
		<div class="row">
			<h3>마이페이지</h3>
		</div>
		<div class="row mt-3">
			<!-- 회원 정보 수정 시작 -->
			<input id="session_check" type="hidden" value="${sessionUser }">
			<div class="col shadow p-3 mb-5 bg-white rounded update-info" style="">
				<h5>회원 정보 수정</h5>
				<a href="${pageContext.request.contextPath}/member/user_info_page.do">수정</a>
			</div>
			<!-- 랭킹 시작 -->
			<div class="col shadow p-3 mb-5 bg-white rounded rank" style="margin-left:10px">
				<h5>랭킹</h5>
				<a href="#">확인</a>
			</div>
		</div>
		
	</div>
	<!-- container 종료 -->
	<script
		src="${pageContext.request.contextPath}/resources/js/member/mypage.js"></script>
</body>