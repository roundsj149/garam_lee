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
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 회원가입</title>
<!-- 시작 -->
<!-- 부트스트랩 css -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<!-- footer css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common/footer.css">

<!-- font관련 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common/font-style.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap">

<!-- 끝 -->
</head>
<body onload="checkToday()">
	<!-- navigation bar(메뉴바) -->
	<div class="container" style="min-height:700px">
		<!-- container 시작 -->
		<div class="row">
			<div class="col"></div>
			<div class="col-6">
				<!-- 회원가입 시작 -->
				<form id="frm"
					action="${pageContext.request.contextPath}/member/join_admin_process.do"
					method="post">
					<input id="session_check" type="hidden" value="${sessionUser }">
					<div class="row mt-5">
						<!-- 회원가입 제목 시작 -->
						<div class="col">
							<h1>관리자 회원가입</h1>
						</div>
					</div>
					<!-- 회원가입 제목끝 -->
					<div class="row mt-3">
						<!-- ID 시작 -->
						<div class="col">
							<!-- 유효성 검사 : 틀린 경우  클래스에 is-invalid 추가, 맞는 경우 클래스에 is-valid 추가-->
							<label for="joinId">아이디(이메일)</label> <input id="joinId"
								name="admin_id" type="email" class="form-control" value=""
								required placeholder="이메일 입력" onfocus="this.placeholder=''"
								onblur="this.placeholder='이메일 입력'" autocomplete="off" required>
							<div id="validationId"></div>
						</div>
					</div>
					<!-- ID 끝 -->
					<!-- PW 시작 -->
					<div class="row mt-3">
						<div class="col">
							<label for="joinPw">비밀번호</label> <input id="joinPw"
								name="admin_pw" type="password" class="form-control" value=""
								required placeholder="비밀번호 입력" onfocus="this.placeholder=''"
								onblur="this.placeholder='비밀번호 입력'" autocomplete="off" required>
							<div id="validationPw"></div>
						</div>
					</div>
					<!-- PW 종료 -->
					<!-- PW 확인 시작 -->
					<div class="row mt-3">
						<div class="col">
							<label for="joinPw2">비밀번호 확인</label> <input id="joinPw2"
								name="member_pw2" type="password" class="form-control" value=""
								required placeholder="비밀번호 입력" onfocus="this.placeholder=''"
								onblur="this.placeholder='비밀번호 입력'" autocomplete="off" required>
							<div id="validationPw2"></div>
						</div>
					</div>
					<!-- PW 확인 종료 -->
					<div class="row mt-3">
						<!-- Nick 시작 -->
						<div class="col">
							<label for="joinNick">닉네임</label> <input id="joinNick"
								name="admin_nickname" type="text" class="form-control" value=""
								required placeholder="닉네임 입력" onfocus="this.placeholder=''"
								onblur="this.placeholder='닉네임 입력'" autocomplete="off" required>
							<div id="validationNick"></div>
						</div>
					</div>
					<!-- Nick 종료 -->
					<div class="row mt-3">
						<div class="col">
							<label for="joinFullname">이름</label> <input id="joinFullname"
								name="admin_fullname" type="text" class="form-control" value=""
								required placeholder="이름 입력" onfocus="this.placeholder=''"
								onblur="this.placeholder='이름 입력'" autocomplete="off" required>
							<div id="validationFullname"></div>
						</div>
					</div>
					<div class="row mt-3">
						<!-- 성별 시작 -->
						<div class="col">
							<label for="adminGender">성별</label>
							<div></div>
							<input class="joinGender" name="admin_gender" type="radio"
								class="form-control" value="M" checked> <label
								class="form-check-label" for="joinGenderRadio">남</label> <input
								class="joinGender" name="admin_gender" type="radio"
								class="form-control" value="W"> <label
								class="form-check-label" for="joinGenderRadio">여</label>
						</div>
					</div>
					<!-- 성별 종료 -->
					<!-- 생년월일 - js -->
					<div id="birthday" class="row mt-3">

					</div>
					<!-- 생년월일 종료 -->
					<div class="row mt-3">
						<!-- 휴대전화 시작 -->
						<div class="col">
							<label for="joinPhone">휴대전화</label> <input id="joinPhone"
								name="admin_phone" type="text" class="form-control" value=""
								required placeholder="휴대전화번호 입력 ex)01012349876"
								onfocus="this.placeholder=''"
								onblur="this.placeholder='휴대전화번호 입력 ex)01012349876'"
								autocomplete="off" required>
							<div id="validationPhone"></div>
						</div>
					</div>
					<!-- 휴대전화 종료 -->
					<div class="row mt-3 mr-3">
						<!-- 회원가입 버튼 시작 -->
						<div class="col-5"></div>
						<div class="col-5"></div>
						<div class="col-2">
							<input type="button" id="joinBtn" class="btn btn-outline-info"
								value="회원가입">
						</div>
					</div>
					<!-- 회원가입 버튼 종료 -->
				</form>
			</div>
			<!-- 회원가입 끝 -->
			<div class="col"></div>
		</div>
	</div>
	<!-- container 종료 -->

	<!-- jquery 3.5.1 -->
	<script src="https://code.jquery.com/jquery-3.5.1.js"
	integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
	crossorigin="anonymous"></script>
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
	<script
		src="${pageContext.request.contextPath}/resources/js/member/join_admin_page.js"></script>
</body>
</html>

