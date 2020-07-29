<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
<title>회원 탈퇴 완료</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/update_password_complete.css">
<script>
	$(function() {
		var second = 5;
		//5초 후 메인파이지로 이동하기
		setTimeout(
				function() {
					location.href = "${pageContext.request.contextPath}/main_page.do";
				}, 5000);
		
		// span에 543210초 후 메인페이지로 이동합니다 출력
		setInterval(function() {
			second--;
			$("#second").text(second);
		}, 1000);
	});
</script>
</head>
<body>
	<!-- 컨테이너 시작 -->
	<div class="wrap" style="position: relative;z-index:1; min-height:700px;  display:flex; justify-content:center; align-items:center;">
		<div class="container-bg" style="text-align:center; flex-direction:column; position: relative;z-index:1; border-radius:20px; width:50%; height:400px; display:flex; justify-content:center; align-items:center;">
			<!-- 중간 내용 -->
			<h4 style="color:#28a745">루트맵 회원 탈퇴가 완료되었습니다</h4>
			<p style="font-size: 14px">
				그동안 루트맵 서비스를 아끼고 사랑해주셔서 감사합니다.<br>더욱더 노력하고 발전하는 루트맵이 되겠습니다<br>
				<span id="second">5</span>초 후 메인페이지로 이동합니다
			</p>
			<a href="${pageContext.request.contextPath}/main_page.do">메인 페이지로</a>
		</div>
	</div>
</body>