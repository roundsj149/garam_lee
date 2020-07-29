<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<%
response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
response.setDateHeader("Expires", 0L); // Do not cache in proxy server
%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<head>
<title>사용자 신고 완료</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/report/report_user_page.css?aa">
</head>
<body>
	<div class="wrap" style="position: relative;z-index:1; min-height:700px;  display:flex; align-items:center; justify-content:center">
		<div class="container"  style="margin:0px; position: relative;z-index:1; border-radius:20px; width:50%; height:400px; display:flex; flex-direction:column;justify-content:center; align-items:center;">
		<!-- container 시작 -->
			<h3>사용자 신고 완료</h3>
			<p class="advice highlight">
				<strong>신고하신 내용이 정상 접수되었습니다</strong>
			</p>
			<p class="advice">
					RouteMap 팀에서 해당 사용자 활동을 검토하여 운영정책 및 서비스 약관에 따라 처리하겠습니다 <br>
			</p>
			<a href="${pageContext.request.contextPath}/${uri }">이전 페이지로</a>
		</div>
		<!-- container 종료 -->
	</div>
</body>