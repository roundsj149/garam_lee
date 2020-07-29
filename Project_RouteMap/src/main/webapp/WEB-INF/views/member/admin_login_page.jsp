<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<%
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
response.setDateHeader("Expires", 0L); // Do not cache in proxy server
%>
<%-- 뒤로가기 방지하는거  차선책 --%>
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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common/font-style.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap">

<!-- jquery 3.5.1 -->
<script src="https://code.jquery.com/jquery-3.5.1.js"
	integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
	crossorigin="anonymous"></script>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>관리자 로그인 페이지</title>
<!-- 로그인 css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/admin_login_page.css">
</head>
<body>
	<!-- 컨테이너 시작 -->
	<div class="container mt-4">
		<div class="row" style="margin-top : 20rem;">
			<div class="col-3">
				<!-- 왼쪽 여백 -->
			</div>
			<form id="frm" action="./admin_login_process.do" method="post">
				<input id="session_check" type="hidden" value="${sessionUser }">
				<div class="col">
					<!-- 중간 내용 -->
					<div class="row text-center">
						<!-- 로고 자리 -->
						<div class="col">
							<img
								src="${pageContext.request.contextPath}/resources/img/routemap_logo.png">
						</div>
					</div>
					<div class="row mt-1">
						<!-- 아이디 -->
						<div class="col">
							<input id="id_check" placeholder="관리자 아이디"
								onfocus="this.placeholder=''" onblur="this.placeholder='관리자 아이디'"
								name="insert_id" type="text" class="form-control">
						</div>
					</div>
					<div class="row mt-1">
						<!-- 비밀번호 -->
						<div class="col">
							<input id="password_check" placeholder="관리자 비밀번호"
								onfocus="this.placeholder=''"
								onblur="this.placeholder='관리자 비밀번호'" name="insert_pw"
								type="password" class="form-control"
								onkeypress="if(event.keyCode==13) {login(); return false;}">
						</div>
					</div>
					<!-- 캡차 -->
					<c:choose>
					<c:when test="${admin_login_trial == -10}">
						<p class="advice">탈퇴한 아이디입니다</p>
					</c:when>
					<c:when test="${admin_login_trial == 0 || admin_login_trial == -1}">
						<p class="advice">
             				가입하지 않은 아이디이거나, 잘못된 비밀번호입니다
						</p>
					</c:when>
					<c:when test="${admin_login_trial == 5}">
						<p class="advice">
							아래의 이미지를 보이는 대로 입력해주세요</p>
						<div class="row mt-1">
							<div class="col-2"></div>
							<div class="col captcha" style="position: relative; height: 100px">
								<img id="captchaImg" title="캡차 이미지"
									src="/member/captchaImg.do" alt="캡차 이미지"
									style="width: 300px; maxheight: 100px; position: absolute">
								<i class="fas fa-sync-alt fa-2x"
									style="z-index: 5; position: absolute; color: #cc3366; top: 50px; left: 270px; cursor: pointer"
									onclick="refreshBtn();return false" aria-hidden="true"></i>
								<div id="captchaAudio" style="display: none;"></div>
							</div>
						</div>
						<div class="row mt-1">
							<div class="col">
								<input type="text" name="answer" id="answer"
									placeholder="자동입력 방지문자" onfocus="this.placeholder=''"
									onblur="this.placeholder='자동입력 방지문자'" class="form-control"
									autocomplete="off">
							</div>
						</div>
					</c:when>
					</c:choose>
					<div class="row mt-1">
						<!-- 로그인 버튼 -->
						<div class="col">
							<input type="button" class="btn btn-primary btn-block"
								value="로그인" onclick="login()"> <input
								name="redirectPage" type="hidden" value="${redirectPage}">
						</div>

					</div>
					<div class="row mt-1 text-center">
						<div class="col-8"></div>
					</div>
					<div class="row mt-2"
						style="margin: 0em 0.3em 0em 0.3em; border-bottom: 1px solid #D5D5D5"></div>
					<div class="row mt-2 text-center" style="margin-right : 1rem;">
						<!-- 아이디 찾기 / 비밀번호 찾기 / 회원가입 -->
						<div class="col">
							<a
								href="${pageContext.request.contextPath}/member/find_memberid_page.do"
								style="font-size: 0.77em">아이디 찾기</a>
						</div>
						<div class="col" style="padding: 0px">
							<a
								href="${pageContext.request.contextPath}/member/find_memberpw_page.do"
								style="font-size: 0.77em">비밀번호 찾기</a>
						</div>
						<div class="col" style="padding: 0px">
							<a
								href="${pageContext.request.contextPath}/member/join_admin_page.do"
								style="font-size: 0.77em">관리자 회원가입</a>
						</div>
					</div>
				</div>
			</form>
			<!-- 위치이동 필요 -->
			<div class="col-3">
				<!-- 오른쪽 여백 -->
			</div>
		</div>
	</div>
	<script
		src="${pageContext.request.contextPath}/resources/js/member/admin_login_page.js"></script>
		<!-- ? 뭔지 모르겠지만 jquery 아래 있어야되요! 아시는분은 수정부탁드려요  -->
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
		integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
		crossorigin="anonymous"></script>
	<!-- 부트스트랩 4.41 js jquery 아래 있어야되요! -->
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
		integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
		crossorigin="anonymous"></script>
	<!-- 폰트어썸 -->
	<script src="https://kit.fontawesome.com/140e7037bd.js"
		crossorigin="anonymous"></script>
</body>
</html>