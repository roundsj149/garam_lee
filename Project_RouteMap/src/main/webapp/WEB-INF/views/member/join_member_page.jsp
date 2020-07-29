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
<title>회원가입</title>
</head>
<body>
	<div class="container" style="min-height:700px">
		<!-- container 시작 -->
		<div class="row mb-4">
			<div class="col"></div>
			<div class="col-6">
				<!-- 회원가입 시작 -->
				<form id="frm"
					action="${pageContext.request.contextPath}/member/join_member_process.do"
					method="post">
					<input id="session_check" type="hidden" value="${sessionUser }">
					<div class="row mt-5">
						<!-- 회원가입 제목 시작 -->
						<div class="col">
							<h1>회원가입</h1>
						</div>
					</div>
					<!-- 회원가입 제목끝 -->
					<div class="row mt-3">
						<!-- ID 시작 -->
						<div class="col">
							<!-- 유효성 검사 : 틀린 경우  클래스에 is-invalid 추가, 맞는 경우 클래스에 is-valid 추가-->
							<label for="joinId">아이디(이메일)</label> <input id="joinId"
								name="member_id" type="email" class="form-control" value=""
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
								name="member_pw" type="password" class="form-control" value=""
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
								name="member_nickname" type="text" class="form-control" value=""
								required placeholder="닉네임 입력" onfocus="this.placeholder=''"
								onblur="this.placeholder='닉네임 입력'" autocomplete="off" required>
							<div id="validationNick"></div>
						</div>
					</div>
					<!-- Nick 종료 -->
					<div class="row mt-3">
						<div class="col">
							<label for="joinFullname">이름</label> <input id="joinFullname"
								name="member_fullname" type="text" class="form-control" value=""
								required placeholder="이름 입력" onfocus="this.placeholder=''"
								onblur="this.placeholder='이름 입력'" autocomplete="off" required>
							<div id="validationFullname"></div>
						</div>
					</div>
					<div class="row mt-3">
						<!-- 성별 시작 -->
						<div class="col">
							<label for="joinGender">성별</label>
							<div></div>
								<label style="cursor:pointer" class="form-check-label" for="joinGenderRadio1"><input class="joinGender" id="joinGenderRadio1" name="member_gender" type="radio" value="M" checked>
								남</label>
								<label style="cursor:pointer" class="form-check-label" for="joinGenderRadio2"><input class="joinGender" id="joinGenderRadio2" name="member_gender" type="radio" value="W">
								여</label>
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
								name="member_phone" type="text" class="form-control" value=""
								required placeholder="휴대전화번호 입력 ex)01012349876"
								onfocus="this.placeholder=''"
								onblur="this.placeholder='휴대전화번호 입력 ex)01012349876'"
								autocomplete="off" required>
							<div id="validationPhone"></div>
						</div>
					</div>
					<!-- 휴대전화 종료 -->
					<div class="row my-3">
						<!-- 회원가입 버튼 시작 -->
						<div class="col text-right">
							<input type="button" id="joinBtn" class="btn btn-outline-success"
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
	<%--bootstrap JS 코드 --%>
	<script
		src="${pageContext.request.contextPath}/resources/js/member/join_member_page.js"></script>
</body>