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
<title>회원 탈퇴</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/withdrawal_page.css">
</head>
<body>
<div class="wrap pb-5 bg-light" style="min-height:700px">
	<div class="container info-container py-3">
		<!-- container 시작 -->
		<div class="row">
			<div class="col">
				<form id="withdrawalFrm"
					action="${pageContext.request.contextPath}/member/withdrawal_process.do"
					method="post">
					<!-- 회원 탈퇴  안내-->
					<div class="row mt-5">
						<div class="col">
							<input id="session_check" type="hidden" value="${sessionUser }">
							<h3><i class="fas fa-minus-circle" style="margin-right:1rem; color:#58AF9E"></i>회원 탈퇴 안내</h3>
						</div>
					</div>
					<div class="row mt-5">
						<div class="col">
							<p class="advice">회원탈퇴를 신청하기 전에 안내 사항을 꼭 확인해주세요.</p>
							<br>
							<p class="advice highlight">
								<strong>탈퇴 후 회원정보 및 개인형 서비스 이용기록은 모두 삭제됩니다.</strong>
							</p>
							<p class="advice">회원정보 및 나의 관심 등 개인형 서비스 이용기록은 모두 삭제되며, 삭제된
								데이터는 복구되지 않습니다. 필요한 데이터는 미리 백업을 해주세요.</p>
							<br>
							<p class="advice highlight">
								<strong>탈퇴 후에도 등록한 게시물은 그대로 남아 있습니다.</strong>
							</p>
							<p class="advice">여행지/맛집, 루트공유, 자유게시판에 올린 게시글 및 댓글은 탈퇴 시 자동
								삭제되지 않고 그대로 남아 있습니다. 삭제를 원하는 게시글이 있다면 반드시 삭제하시기 바랍니다. 탈퇴 후에는
								회원정보가 삭제되어 본인 여부를 확인할 수 있는 방법이 없어, 게시글을 임의로 삭제해드릴 수 없습니다.</p>
							<br>
							<p class="advice highlight">
								<strong>사용하고 계신 아이디(${sessionUser.member_id })는 탈퇴할 경우 재사용 및 복구가 불가능합니다.</strong>
							</p>
							<p class="advice">탈퇴한 아이디는 본인과 타인 모두 재사용 및 복구가 불가하오니 신중하게 선택하시기 바랍니다.</p>

						</div>
					</div>
					<!-- 탈퇴 안내사항 -->
					<div class="row mt-3"></div>
					<!-- 비밀번호 입력-->
					<div class="row mt-3">
						<div class="col">
							<label for="currentPw">비밀번호 입력</label>
							<input id="currentPw"
								name="current_pw" type="password" class="form-control" value=""
								placeholder="현재 비밀번호" onfocus="this.placeholder=''"
								onblur="this.placeholder='현재 비밀번호'" autocomplete="off" required>
							<div id="validationCurrentPw"></div>
						</div>
					</div>
					<!-- 동의 체크 -->
					<div class="row mt-3">
						<div class="checkbox-container col form-check-inline" style="padding-left:5px">
							<input id="agreementCheck" class="form-check-input" name="agreementCheck" type="checkbox" value="Y">
							<label id="agreement" class="form-check-label" for="agreementCheck">
								<strong>안내 사항을 모두 확인하였으며, 이에 동의합니다.</strong>
							</label>
						</div>
					</div>
					<!-- 확인, 취소 버튼 -->
					<div class="withdrawal-btn row mt-5">
						<span id="withdrawalBtn" class="btn btn-outline-secondary mr-2">회원탈퇴</span>
						<a href="${pageContext.request.contextPath }/member/user_info_page.do" class="btn btn-success">취소</a>
					</div>
				</form>
			</div>
			<!-- 회원가입 끝 -->
		</div>
	</div>
	<!-- container 종료 -->
	<script
		src="${pageContext.request.contextPath}/resources/js/member/withdrawal_page.js"></script>
</div>
</body>