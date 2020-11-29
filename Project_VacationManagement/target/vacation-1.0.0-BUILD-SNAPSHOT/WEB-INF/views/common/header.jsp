<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/header.css">
<div class="header">
	<div>
		<a href="/vacation/list">
			<img src="${pageContext.request.contextPath}/resources/img/dayside-footer-logo.png"
				onmouseover="this.src='${pageContext.request.contextPath}/resources/img/dayside-logo.png'"
				onmouseout="this.src='${pageContext.request.contextPath}/resources/img/dayside-footer-logo.png'">
		</a>
		<c:if test="${!empty memberSession}">
		<a href="/vacation/list" class="btn btn-warning btn-sm">휴가 신청</a>
		<a href="/overtime/list" class="btn btn-dark btn-sm">초과근무 신청</a>
		</c:if>
	</div>
	<c:if test="${!empty memberSession}">
		<form id="logoutForm" action="/member/logout">
			올해 사용한 휴가 일수 : <span id="countVacation"></span>
			<input id="pwUpdateBtn" type="button" class="btn btn-danger btn-sm" value="비밀번호 변경">
			<input type="submit" class="btn btn-secondary btn-sm" value="로그아웃">
		</form>
	</c:if>
</div>
<script src="${pageContext.request.contextPath}/resources/js/member/update_pw_page.js"></script>