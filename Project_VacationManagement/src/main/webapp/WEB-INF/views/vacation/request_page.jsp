<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<!-- 부트스트랩  CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<!-- CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/vacation/request_page.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/font.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/common.css">
<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<!-- DatePicker -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
<!-- DatePicker CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker3.standalone.min.css">
<!-- Font Awesome -->
<script src="https://kit.fontawesome.com/140e7037bd.js" crossorigin="anonymous"></script>

<!-- favicon -->
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon.ico">
<!-- font -->
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
<meta charset="UTF-8">
<title>데이사이드 휴가 신청</title>
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="container">
	<div class="request-box">
		<div class="title-box">
			<div class="logo">
				<img src="${pageContext.request.contextPath}/resources/img/dayside-logo.png">
			</div>
			<div class="logo"><h3>휴가 신청</h3></div>
		</div>
		<form id="frm">
			<div class="form-content">
				<label for="employeeNo">사번</label>
			    <div class="info" id="emailId">${map.employeeNo }</div>
			</div>
			<div class="form-content">
			    <label for="fullname">이름</label>
			    <div class="info" id="fullname">${map.fullname }</div>
			</div>
			<div class="form-content">
			    <label for="fullname">시작일</label>
			    <input type="text" id="startDate" class="form-control">
			    <div class="btn-group btn-group-toggle radio-button" data-toggle="buttons">
					<label class="btn btn-secondary active">
						<input type="radio" name="startType" id="startMorning" value="MORNING"> 오전
					</label>
					<label class="btn btn-secondary">
						<input type="radio" name="startType" id="startAfternoon" value="AFTERNOON"> 오후
					</label>
					<label class="btn btn-secondary">
						<input type="radio" name="startType" id="startAllDay" value="ALLDAY"> 종일
					</label>
				</div>
			</div>
			<div class="form-content">
			    <label for="fullname">종료일</label>
			    <input type="text" id="endDate" class="form-control">
			    <div id="endRadioBtn" class="btn-group btn-group-toggle radio-button" data-toggle="buttons">
					<label class="btn btn-secondary active">
						<input type="radio" name="endType" id="endMorning" value="MORNING"> 오전
					</label>
					<label class="btn btn-secondary">
						<input type="radio" name="endType" id="endAllDay" value="ALLDAY"> 종일
					</label>
				</div>
			</div>
			<div id="modal-align" class="modal" tabindex="-1">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-body">
			        <p>휴가 유형을 선택해주세요</p>
			      </div>
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">확인</button>
			    </div>
			  </div>
			</div>
			<div class="request-button">
			    <input type="button" id="requestBtn" class="btn btn-warning" value="신청하기">
			    <a href="list" class="a-box">
			    	<button type="button" class="btn btn-outline-secondary">취소하기</button>
			    </a>
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
<script src="${pageContext.request.contextPath}/resources/js/vacation/request_page.js"></script>
</body>
</html>