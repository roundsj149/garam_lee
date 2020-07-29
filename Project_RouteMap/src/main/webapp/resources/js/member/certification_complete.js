$(function() {
	var second = 5;
	// 5초 후 메인파이지로 이동하기
	setTimeout(
			function() {
				location.href = "/member/login_page.do";
			}, 5000);

	// span에 543210초 후 메인페이지로 이동합니다 출력
	setInterval(function() {
		second--;
		$("#second").text(second);
	}, 1000);
});