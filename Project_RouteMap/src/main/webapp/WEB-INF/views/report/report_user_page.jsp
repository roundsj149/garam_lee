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
<title>사용자 신고</title>
<script
	src="${pageContext.request.contextPath}/resources/js/report/report_user_page.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/report/report_user_page.css">
</head>
<body>
	<div class="container" style="min-height:700px">
		<!-- container 시작 -->
		<div class="row">
			<div class="col"></div>
			<div class="col-8 py-5">
				<form id="frm"
					action="${pageContext.request.contextPath}/report/report_user_process.do" method="post">
					<div class="header" style="display: flex; flex-direction: column;">
						<h3>사용자 신고</h3>
						<br>
						<p class="advice highlight">
							<strong>사용자를 신고합니다</strong>
						</p>
						<p class="advice">
							허위신고일 경우, 신고자의 서비스 활동이 제한될 수 있으니, 유의하시어 신중하게 신고해주세요<br>
							신고해주신 내용은 운영정책 및 서비스 약관에 따라 처리됩니다
						</p>
					</div>
					<div class="header">
						<div style="padding: 20px; display: grid; grid-template-columns: repeat(2, 1fr); border: 1px solid #ccc">
							<strong style="font-size: 14px;">신고 사유</strong>
							<br><br>
							<label for="insult"><input id="insult" class="radioButton" type="radio" name="report_reason" value="1" checked>&nbsp;욕설/인신공격</label>
							<label for="infringement"><input id="infringement" class="radioButton" type="radio" name="report_reason" value="2">&nbsp;권리침해 및 사이버 괴롭힘</label>
							<label for="fraud"><input id="fraud" class="radioButton" type="radio" name="report_reason" value="3">&nbsp;사기</label>
							<label for="advertisement"><input id="advertisement" class="radioButton" type="radio" name="report_reason" value="4">&nbsp;홍보/영리목적글 게시</label>
							<label for="illegality"><input id="illegality" class="radioButton" type="radio" name="report_reason" value="5">&nbsp;불법정보 게시</label>
							<label for="etc"><input id="etc" class="radioButton" type="radio" name="report_reason" value="6">&nbsp;기타</label>
						</div>
					</div>
					<div class="row mt-3 mr-0" style="display: flex; justify-content: flex-end">
						<input type="hidden" name="member_no" value="${param.member_no }">
						<input type="hidden" name="uri" value="${param.uri }">
						<input type="submit" value="신고" id="report_user" class="btn btn-success mr-1">
						<div class="btn btn-outline-secondary" onclick="history.go(-1)">취소</div>
					</div>
				</form>
			</div>
			<div class="col"></div>
		</div>
	</div>
</body>





















