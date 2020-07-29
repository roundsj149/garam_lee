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
<title>회원 정보 수정</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/user_info_page.css">
</head>
<body>
<div class="wrap pb-5 bg-light" style="min-height:700px">
	<div class="container info-container py-3">
		<!-- container 시작 -->
		<div class="header">
		<input id="session_check" type="hidden" value="${sessionUser }">
			<div class="col pl-0">
				<h3><i class="far fa-edit" style="margin-right:1rem; color:#58AF9E"></i>회원 정보 수정</h3>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<p class="advice">
					<strong id="idInfo">${memberVo.member_id }</strong> 님의 회원정보입니다.
				</p>
			</div>
		</div>
		<!-- 테이블 -->
		<table class="tbl_model">
			<tbody>
			<!-- 아이디 -->
				<tr>
					<th>아이디</th>
					<td><span class="content">${memberVo.member_id }</span>
						<a href="${pageContext.request.contextPath}/member/withdrawal_page.do" id="withdrawal" class="btn btn-outline-secondary btn-sm">탈퇴하기</a>
					</td>
				</tr>
				<!-- 비밀번호 -->
				<tr>
					<th>비밀번호</th>
					<td>
						<a href="${pageContext.request.contextPath}/member/update_password_page.do" class="btn btn-success btn-sm">수정하기</a>
					</td>
				</tr>
				<!-- 닉네임 -->
				<tr>
					<th>닉네임</th>
					<td>
						<form id="nicknameFrm" action="${pageContext.request.contextPath}/member/update_nickname_process.do">
							<span class="content">${memberVo.member_nickname }</span>
							<span id="nicknameUpdateBtn" class="btn btn-success btn-sm">수정하기</span>
							<div id="nicknameUpdate" class="col" style="display: none">
								<div class="col">
									<div class="row" style="display:flex; flex-direction:column; width:300px;">
										<label class="label" for="updateNick">변경할 닉네임</label>
										<input
													id="updateNick" name="member_nickname" type="text"
													class="form-control form-control-sm" value="" required
													placeholder="닉네임 입력" onfocus="this.placeholder=''"
													onblur="this.placeholder='닉네임 입력'" autocomplete="off"
													required>
										<div id="validationNick"></div>
										<div class="col" style="display:flex; padding:10px 0px">
											<div id="cancelUpdateNickname" class="col mr-2 btn btn-outline-success btn-sm">수정취소</div>
											<div id="updateNickBtn" class="col btn btn-outline-success btn-sm">수정완료</div>
										</div>
									</div>
								</div>
							</div>
						</form>
					</td>
				</tr>
				<!-- 이름 -->
				<tr>
					<th>이름</th>
					<td><span class="content">${memberVo.member_fullname }</span>
						<p class="advice">이름을 변경하시려면 고객센터 1:1문의를 이용해주세요</p>
					</td>
				</tr>
				<!-- 생년월일 -->
				<tr>
					<th>생년월일</th>
					<td>
						<form id="birthdayFrm" action="${pageContext.request.contextPath}/member/update_birthday_process.do">
							<fmt:parseDate value="${memberVo.member_birth }" var="birthday" pattern="yyyyMMdd" />
							<fmt:formatDate var="birthdaySplit" value="${birthday}" pattern="yyyy-MM-dd" />
							<c:set var="year1" value="${fn:split(birthdaySplit,'-')[0]}" />
							<c:set var="month1" value="${fn:split(birthdaySplit,'-')[1]}" />
							<c:set var="day1" value="${fn:split(birthdaySplit,'-')[2]}" />
							<div class="row" style="display:flex; align-items:center;">
								<div class="col">
									<select name="1" disabled>
										<option value="year"><c:out value="${year1 }"></c:out></option>
									</select>
									<select name="2" disabled>
										<option value="month"><c:out value="${month1 }"></c:out></option>
									</select>
									<select name="3" disabled>
										<option value="day"><c:out value="${day1 }"></c:out></option>
									</select>
									<span id="birthdayUpdateBtn" class="btn btn-success btn-sm" style="margin-left:10px">수정하기</span>
								</div>
							</div>
							<div id="birthdayUpdate" class="col pl-0">
								<div id="birthday" class="col mt-3">
								<!-- 생년월일 - js -->
								</div>
								<div class="col" style="padding: 15px; display:flex; max-width:330px">
									<div id="cancelUpdateBirthday" class="col mr-2 btn btn-outline-success btn-sm">수정취소</div>
									<div id="updateBirthdayBtn" class="col btn btn-outline-success btn-sm">수정완료</div>
								</div>
							</div>
						</form>
					</td>
				</tr>
						<!-- 휴대전화 -->
						<tr>
							<th>휴대전화</th>
							<td>
								<form id="phoneFrm"
									action="${pageContext.request.contextPath}/member/update_phone_process.do">
									<span id="phoneUpdateBtn" class="btn btn-success btn-sm">수정하기</span>
									<div id="phoneUpdate" class="col" style="display: none">
										<div class="col">
											<div class="row" style="display:flex; flex-direction:column; width:300px;">
												<label class="label" for="updatePhone">변경할 휴대전화번호</label>
												<input
													id="updatePhone" name="member_phone" type="text" style="width:300px"
													class="form-control form-control-sm" value="" required
													placeholder="휴대전화번호 입력 ex)01012349876"
													onfocus="this.placeholder=''"
													onblur="this.placeholder='휴대전화번호 입력 ex)01012349876'"
													autocomplete="off">
												<div id="validationPhone"></div>
												<div class="col" style="display:flex; padding:10px 0px">
													<div id="cancelUpdatePhone" class="col mr-2 btn btn-outline-success btn-sm">수정취소</div>
													<div id="updatePhoneBtn" class="col btn btn-outline-success btn-sm">수정완료</div>
												</div>
											</div>
										</div>
									</div>
								</form>
							</td>
						</tr>
					</tbody>
				</table>
	</div>
	</div>
	<!-- 회원가입 끝 -->
	<!-- container 종료 -->
	<script
	src="${pageContext.request.contextPath}/resources/js/member/user_info_page.js"></script>
</body>