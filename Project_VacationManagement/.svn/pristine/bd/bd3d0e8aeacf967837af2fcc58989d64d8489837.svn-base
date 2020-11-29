<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<!-- 부트스트랩  CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<!-- CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/vacation/list.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/font.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/common.css">
<!-- Font Awesome -->
<script src="https://kit.fontawesome.com/140e7037bd.js" crossorigin="anonymous"></script>
<!-- favicon -->
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon.ico">
<!-- font -->
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
<meta charset="UTF-8">
<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<title>데이사이드 휴가 신청</title>
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="container">
	<div class="list-box">
		<div class="title-box">
			<div class="logo">
				<img src="${pageContext.request.contextPath}/resources/img/dayside-logo.png">
			</div>
			<div class="logo"><h3>초과근무 신청 목록</h3></div>
		</div>
		<div class="form-content" id="content">
			<div id="menuBox">
				
			</div>
			<table class="table table-hover table-bordered" >
	            <thead class="thead-light">
	                <tr>
	                    <th>신청일</th><th>출근일시</th><th>퇴근일시</th><th>근무 형태</th><th>업무 내용</th><th>신청자</th>
	                </tr>
	            </thead>
	            <tbody>
	            	<!-- 초과근무 신청 목록 부분 -->
	            </tbody>
	        </table>
	    </div>
	    <!-- 업무 내용 모달 -->
	    <div id="modal-align" class="modal" tabindex="-1">
			<div class="modal-dialog modal-dialog-scrollable">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">업무내용</h5>
					</div>
			    	<div class="modal-body">
			        	<p></p>
			      	</div>
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
			    </div>
			</div>
		</div>
	    <!--  페이지 내비게이션 시작 -->
        <nav aria-label="Page navigation example" style="display:grid; grid-template-columns:6fr auto;">
        	<ul id="page_navi" class="pagination mt-3">
        	
        	</ul>
        </nav>
        <!--  페이지 내비게이션 끝 -->
		<div class="button-box">
			<a href="/overtime/request"><input type="button" class="btn btn-dark" value="초과근무 신청"></a>
		</div>
	</div>
</div>
<!-- Popper -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" 
	integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<!-- Bootstrap JS-->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" 
	integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
<jsp:include page="../common/footer.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/resources/js/overtime/list.js"></script>
</body>
</html>