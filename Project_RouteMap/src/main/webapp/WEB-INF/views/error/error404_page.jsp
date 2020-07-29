<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<script src="https://kit.fontawesome.com/140e7037bd.js"
	crossorigin="anonymous"></script>
<title>Insert title here</title>
<style>
.container {
	position: absolute;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	align-items: center;
	justify-content: center;
	display: -webkit-flex; -webkit-align-item; center;
	flex-direction:column;
	-webkit-justify-content: center;
}

p {
	font-size:15px;
	 margin-top:10px;
	 margin-bottom:10px
}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">
				<img
					src="${pageContext.request.contextPath}/resources/img/routemap_logo.png">
			</div>
		</div>
		<div class="row mt-3">
			<div class="col text-center">
				<img alt="페이지를 찾을 수 없습니다"
					src="${pageContext.request.contextPath}/resources/img/error/error404.png">
				<p>
					지금 입력하신 주소의 페이지는 사라졌거나 다른 페이지로 변경되었습니다.<br>
					주소를 다시 확인해주세요<br>
				</p>
				<a href="${pageContext.request.contextPath}/main_page.do">메인 페이지로</a>
			</div>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
		integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
		integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
		crossorigin="anonymous"></script>
</body>
</body>
</html>