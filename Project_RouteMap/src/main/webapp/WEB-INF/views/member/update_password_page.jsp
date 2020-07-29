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
<title>비밀번호 변경</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/update_password_page.css">
</head>
<body>
<div class="wrap pb-5 bg-light" style="min-height:700px">
	<div class="container info-container py-3">
		<!-- container 시작 -->
		<div class="row">
			<div class="col">
				<form id="pwFrm"
					action="${pageContext.request.contextPath}/member/update_pw_process.do" method="post">
					
					<!-- 비밀번호 변경 -->
					<input id="session_check" type="hidden" value="${sessionUser }">
					<div class="row mt-5">
						<div class="col">
							<h3 style="margin-bottom: 50px"><i class="fas fa-lock" style="margin-right:1rem; color:#58AF9E"></i>
							비밀번호 변경</h3>
						</div>
					</div>
					<!-- 현재 비밀번호-->
					<div class="row">
						<div class="col">
							<p class="advice">기존 비밀번호와 새 비밀번호를 입력해주세요</p>
							<p class="advice">비밀번호는 영문,숫자,특수문자 포함 8~15자리로 입력하셔야합니다</p>
						</div>
					</div>
					<div class="row mt-3">
						<div class="col">
							<label for="currentPw">현재 비밀번호</label>
								<input id="currentPw"
								name="current_pw" type="password" class="form-control" value=""
								placeholder="현재 비밀번호" onfocus="this.placeholder=''"
								onblur="this.placeholder='현재 비밀번호'" autocomplete="off" required>
							<div id="validationCurrentPw"></div>

						</div>
					</div>
					<!-- 새 비밀번호-->
					<div class="row mt-3">
						<div class="col">
							<label for="newPw">새 비밀번호</label>
							<input id="newPw" name="new_pw"
								type="password" class="form-control" value=""
								placeholder="새 비밀번호" onfocus="this.placeholder=''"
								onblur="this.placeholder='새 비밀번호'" autocomplete="off" required>
							<div id="validationNewPw"></div>

						</div>
					</div>
					<!-- 새 비밀번호 확인-->
					<div class="row mt-3">
						<div class="col">
							<label for="newPw2">새 비밀번호 확인</label>
							<input id="newPw2"
								name="new_pw2" type="password" class="form-control" value=""
								placeholder="새 비밀번호 확인" onfocus="this.placeholder=''"
								onblur="this.placeholder='새 비밀번호 확인'" autocomplete="off"
								required>
							<div id="validationNewPw2"></div>

						</div>
					</div>
					<!-- 변경, 취소 버튼 -->
					<div class="row mt-3 text-right" style="margin-bottom:20px">
						<div class="col">
							<span id="updatePwBtn" class="btn btn-success mr-2">변경</span>
							<a
								href="${pageContext.request.contextPath }/member/user_info_page.do"
								class="btn btn-outline-success" style="float:right">취소</a>
						</div>
					</div>
				</form>
			</div>

			<!-- 회원가입 끝 -->
		</div>
	</div>
	<!-- container 종료 -->
	<script
		src="${pageContext.request.contextPath}/resources/js/member/update_password_page.js"></script>
</div>
</body>