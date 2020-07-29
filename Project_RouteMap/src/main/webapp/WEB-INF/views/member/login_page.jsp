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
<head>
<title>로그인 페이지</title>
<!-- 로그인 css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/login_page.css">
<script>
	// 아이디, 비밀번호 입력 확인
	function login() {
		var frm = $('#frm');
		var idCheck = $('#id_check').val();
		var passwordCheck = $('#password_check').val();
		if (idCheck == null || idCheck == "") {
			alert('아이디를 입력해주세요.');
			return false;
		}

		if (passwordCheck == null || passwordCheck == "") {
			alert('비밀번호를 입력해주세요.');
			return false;
		}

		frm.submit();
	}
</script>
</head>
<body>
<!-- wrap -->
<div class="wrap pt-3 pb-5" style="background-color:#f6f6f6; min-height:700px" >
	<!-- 컨테이너 시작 -->
	<div class="container" >
		<div class="row">
			<div class="col-1">
				<!-- 왼쪽 여백 -->
			</div>
			<div class="login col">
				<form id="frm" action="./login_process.do" method="post">
					<input id="session_check" type="hidden" value="${sessionUser }">
					<div class="login_box col">
						<!-- 중간 내용 -->
						<div class="row text-center">
							<!-- 로고 자리 -->
							<div class="col">
								<img
									src="/resources/img/Logo_text.png">
							</div>
						</div>
						<div class="row mt-1">
							<!-- 아이디 -->
							<div class="col">
								<input id="id_check" style="border-radius:20px" placeholder="이메일 입력"
									onfocus="this.placeholder=''" onblur="this.placeholder='이메일 입력'"
									name="insert_id" type="text" class="form-control">
							</div>
						</div>
						<div class="row mt-1">
							<!-- 비밀번호 -->
							<div class="col">
								<input id="password_check" style="border-radius:20px" placeholder="비밀번호 입력"
									placeholder="비밀번호 입력" onfocus="this.placeholder=''"
									onblur="this.placeholder='비밀번호 입력'" name="insert_pw"
									type="password" class="form-control"
									onkeypress="if(event.keyCode==13) {login(); return false;}">
							</div>
						</div>
						<!-- 캡차 -->
						<c:choose>
						<c:when test="${member_login_trial == -10}">
							<p class="advice">탈퇴한 아이디입니다</p>
						</c:when>
						<c:when test="${member_login_trial == 0 || member_login_trial == -1}">
							<p class="advice">
	             				가입하지 않은 아이디이거나, 잘못된 비밀번호입니다
							</p>
						</c:when>
						<c:when test="${member_login_trial == 5}">
							<p class="advice text-center">
								아래의 이미지를 보이는 대로 입력해주세요</p>
							<div class="row mt-2 text-center">
								<div class="col captcha" style="position: relative; height: 100px">
									<img id="captchaImg" title="캡차 이미지"
										src="/member/captchaImg.do" alt="캡차 이미지"
										style="width: 300px; maxheight: 100px;">
									<i class="captcha_reload fas fa-sync-alt fa-2x"
										onclick="refreshBtn();return false" aria-hidden="true"></i>
								</div>
							</div>
							<div class="row mt-1">
								<div class="col">
									<input type="text" name="answer" id="answer" style="border-radius:20px"
										placeholder="자동입력 방지문자" onfocus="this.placeholder=''"
										onblur="this.placeholder='자동입력 방지문자'" class="form-control"
										autocomplete="off">
								</div>
							</div>
						</c:when>
						</c:choose>
						<div class="row mt-3">
							<!-- 로그인 버튼 -->
							<div class="col">
								<input type="button" class="btn btn-success btn-block" style="border-radius:20px"
									value="로그인" onclick="login()"> <input
									name="redirectPage" type="hidden" value="${redirectPage}">
							</div>
	
						</div>
						<!-- 회원가입 버튼 -->
						<div class="row mt-1">
							<div class="col">
								<a class="btn btn-outline-success btn-block" style="border-radius:20px"
									href="${pageContext.request.contextPath}/member/join_member_page.do">회원가입</a>
							</div>
						</div>
						<div class="row mt-2">
							<!-- 로그인 유지 체크 박스 -->
							<div class="checkbox-container col form-check form-check-inline">
								<input id="keepLogin" class="keepLogin form-check-input" type="checkbox" name="useCookie" value="AAA">
								<label class="form-check-label keepLogin" for="keepLogin">&nbsp;로그인 유지하기</label>
							</div>
						</div>
						<div class="row mt-2"
							style="margin: 0em 0.3em 0em 0.3em; border-bottom: 1px solid #D5D5D5"></div>
						<div class="row mt-2 text-center">
							<!-- 아이디 찾기 / 비밀번호 찾기 / 회원가입 -->
							<div class="col-2"></div>
							<!-- 
							<div class="col-3">
								<a
									href="${pageContext.request.contextPath}/member/find_memberid_page.do"
									style="font-size: 0.77em">아이디 찾기</a>
							</div>
							<div class="col-2" style="padding: 0px">
								<a
									href="${pageContext.request.contextPath}/member/find_memberpw_page.do"
									style="font-size: 0.77em">비밀번호 찾기</a>
							</div>
							 -->
						</div>
					</div>
				</form>	
				<div class="background col">
					</div>		
			</div>
			<!-- 위치이동 필요 -->
			<div class="col-1">
				<!-- 오른쪽 여백 -->
			</div>
		</div>
	</div>
	<!-- wrap -->
</div>	
	<script
		src="${pageContext.request.contextPath}/resources/js/member/login_page.js"></script>
</body>
</html>