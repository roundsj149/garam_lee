// 세션 없으면 권한없음 페이지로 이동
$(function() {
	if($("#session_check").val() == "") {
		location.href="/error/no_authority_page.do";
		return;
	}
});

// 현재 비밀번호 확인
var currentPw = $('#currentPw');
var validationCurrentPw = $('#validationCurrentPw');

currentPw.keyup(function() {
	var currentPwCheck = currentPw.val();

	$
			.ajax({
				url : "/member/checkPw_process.do",
				type : "post",
				data : {
					"pw" : currentPwCheck
				},
				dataType : "json",
				success : function(result) {
					if (result == true) { // 비밀번호가 틀렸을 때
						if (currentPwCheck == "") {
							currentPw.attr('class',
									'form-control');
						} else {
							currentPw.attr('class',
									'form-control is-invalid');
							validationCurrentPw.attr('class','invalid-feedback')
							validationCurrentPw.text('비밀번호가 일치하지 않습니다');
							return;
						}

					} else if (result == false) { // 비밀번호가 맞았을 때
						currentPw.attr('class',
								'form-control is-valid');
						validationCurrentPw.attr('class','valid-feedback')
						validationCurrentPw.text('비밀번호가 일치합니다');
						return;
					}
				}
			});

});

// 새 비밀번호 유효성 검사
var newPw = $('#newPw');
var validationNewPw = $('#validationNewPw');

newPw
		.keyup(function() {

			var newPwCheck = newPw.val();
			var newPwRegExp = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,15}$/;
			newPw2.keyup();
			// 검증에 사용할 정규식 변수 regExp에 저장
			if (newPwCheck == "") {
				newPw.attr('class', 'form-control');
			} else if (newPwCheck.match(newPwRegExp) != null) {
				newPw.attr('class', 'form-control is-valid');
				validationNewPw.attr('class','valid-feedback');
				validationNewPw.text('안전한 비밀번호입니다');
			} else {
				newPw.attr('class', 'form-control is-invalid');
				validationNewPw.attr('class','invalid-feedback');
				validationNewPw.text('비밀번호는 영문,숫자,특수문자 포함 8~15자리로 입력하셔야합니다');
				return;
			}
		});

// 새 비밀번호 확인
var newPw = $('#newPw');
var newPw2 = $('#newPw2');
var validationNewPw2 = $('#validationNewPw2');

newPw2.keyup(function() {

	var newPwCheck = newPw.val();
	var newPw2Check = newPw2.val();

	// 검증에 사용할 정규식 변수 regExp에 저장
	if (newPw2Check == "") {
		newPw2.attr('class', 'form-control');
	} else if (newPw2Check == newPwCheck) {
		newPw2.attr('class', 'form-control is-valid');
		validationNewPw2.attr('class','valid-feedback');
		validationNewPw2.text('비밀번호가 일치합니다.');
	} else if (newPw2Check != newPwCheck) {
		newPw2.attr('class', 'form-control is-invalid');
		validationNewPw2.attr('class','invalid-feedback');
		validationNewPw2.text('비밀번호가 다릅니다');
		return;
	}
});

$("#updatePwBtn").click(function() {
	
	if ($("#currentPw").val() == "") {
		alert('현재 비밀번호를 입력하세요');
		return;
	}
	if ($("#newPw").val() == "") {
		alert('새 비밀번호를 입력하세요');
		return;
	}
	if ($("#newPw2").val() == "") {
		alert('새 비밀번호를 확인하세요');
		return;
	}
	if($("#currentPw").attr('class') == "form-control is-invalid") {
		alert('현재 비밀번호를 확인해주세요');
		return;
	}
	if($("#newPw").attr('class') == "form-control is-invalid") {
		alert('새 비밀번호 형식을 확인하세요');
		return;
	}
	if($("#newPw2").attr('class') == "form-control is-invalid") {
		alert('새 비밀번호를 다시 확인하세요');
		return;
	}
	if(document.querySelector('.form-control is-invalid') == null) {
		$('#pwFrm').submit();
	}
});