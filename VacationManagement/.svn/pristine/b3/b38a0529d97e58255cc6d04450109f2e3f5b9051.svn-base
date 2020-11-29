<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- 부트스트랩  CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<!-- CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/member/login_page.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/font.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/common.css">
<!-- Font Awesome -->
<script src="https://kit.fontawesome.com/140e7037bd.js" crossorigin="anonymous"></script>
<!-- favicon -->
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon.ico">
<!-- font -->
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap" rel="stylesheet">
<meta name="viewport" content="width=device-width,initial-scale=1">
<meta charset="UTF-8">
<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<title>데이사이드 휴가 신청</title>
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="container">
	<div class="login-box">
		<div class="title-box">
			<div class="logo">
				<img src="${pageContext.request.contextPath}/resources/img/dayside-logo.png">
			</div>
			<h3>회원 가입</h3>
		</div>
		<form id="frm">
			<div class="form-content">
				<label for="employeeNo">사번</label>
			    <input type="text" class="form-control" id="employeeNo" name="employeeNo" placeholder="사번(6자리 숫자)" onfocus="this.placeholder=''"
								onblur="this.placeholder='사번(6자리 숫자)'" autocomplete="off">
				<div class="no-check"></div>
			</div>
			<div class="form-content">
				<label for="emailId">이메일 아이디</label>
			    <input type="email" class="form-control" id="emailId" name="emailId" placeholder="데이사이드 이메일 주소" onfocus="this.placeholder=''"
								onblur="this.placeholder='데이사이드 이메일 주소'" autocomplete="off">
				<div class="id-check"></div>
			</div>
			<div class="form-content">
			    <label for="fullname">이름</label>
			    <input type="text" class="form-control" id="fullname" name="fullname" placeholder="이름(한글)" onfocus="this.placeholder=''"
								onblur="this.placeholder='이름(한글)'" autocomplete="off">
				<div class="fullname-check"></div>
			</div>
			<div class="form-content">
			    <label for="pw">비밀번호</label>
			    <input type="password" class="form-control" id="pw" name="pw" placeholder="비밀번호(8자리 이상)" onfocus="this.placeholder=''"
								onblur="this.placeholder='비밀번호(8자리 이상)'" autocomplete="off">
				<div class="pw-check"></div>
			</div>
			<div class="form-content">
			    <label for="pwCheck">비밀번호 확인</label>
			    <input type="password" class="form-control" id="pwCheck" name="pwCheck" placeholder="비밀번호 확인" onfocus="this.placeholder=''"
								onblur="this.placeholder='비밀번호 확인'" autocomplete="off">
				<div class="pw-double-check"></div>
			</div>
			<div class="form-content">
				<label for="pwCheck">소속팀</label><br>
			  	<select name="team" id="team">
			  		<option value="DEFAULT">-------------------------</option>
			    	<option value="CEO_SUPPORT">대표이사&amp;경영지원</option>
			    	<option value="DESIGN_QA">디자인&amp;QA</option>
			    	<option value="SOFTWARE_LAB">소프트웨어 연구소</option>
			    	<option value="SERVICE_DEV">서비스개발</option>
			    	<option value="NEW_BUSINESS_DEV">신사업개발</option>
			    	<option value="CONVERGENCE">컨버전스</option>
			  	</select>
			  	<div class="team-check"></div>
			</div>
			<div class="form-content form-grid">
			    <label>팀장 여부</label>
			    <label>관리자 여부</label>
				<div class="form-content">
					<label for="leaderY">
						<input type="radio" name="leaderYn" value="Y" id="leaderY">예&nbsp;&nbsp;
					</label>
					<label for="leaderN">
						<input type="radio" name="leaderYn" value="N" id="leaderN">아니오
					</label>
					<div class="leader-check"></div>
				</div>
				<div class="form-content">
					<label for="adminY">
						<input type="radio" name="adminYn" value="Y" id="adminY">예&nbsp;&nbsp;
					</label>
					<label for="adminN">
						<input type="radio" name="adminYn" value="N" id="adminN">아니오
					</label>
					<div class="admin-check"></div>
				</div>
			</div>
			<div class="form-content">
				<div class="info-check alert alert-danger" role="alert"></div>
			</div>
			<div class="form-content btns">
			    <input type="button" class="btn btn-warning" id="joinBtn" value="가입하기">
			    <a class="a-box" href="/member/login"><button type="button" class="btn btn-outline-secondary">취소</button></a>
			</div>
		</form>
	</div>
</div>
<!-- Popper -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" 
	integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<!-- Bootstrap JS-->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" 
	integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
<jsp:include page="../common/footer.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/resources/js/member/join_page.js"></script>
</body>
</html>