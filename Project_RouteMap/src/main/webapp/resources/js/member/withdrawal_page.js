$(function() {
	if($("#session_check").val() == "") {
		location.href="/error/no_authority_page.do";
		return;
	}
});

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
							validationCurrentPw.attr('class','invalid-feedback');
							validationCurrentPw.text('비밀번호가 일치하지 않습니다');
							return;
						}

					} else if (result == false) { // 비밀번호가 맞았을 때
						currentPw.attr('class',
								'form-control is-valid');
						validationCurrentPw.attr('class','valid-feedback');
						validationCurrentPw.text('비밀번호가 일치합니다');
						return;
					}
				}
			});
});

$("#withdrawalBtn").click(function() {
	// 동의 체크했을 때
	if($("input:checkbox[id='agreementCheck']").prop("checked")) {
		if($("#currentPw").val() == "") {	// 비밀번호 입력x
			alert("비밀번호를 입력하셔야 합니다.");
			return;
		}
		if($("#currentPw").attr('class') == "form-control is-invalid") {	// 비밀번호 불일치
			alert('비밀번호를 확인해주세요');
			return;
		}
		if(document.querySelector('.form-control is-invalid') == null) {
			if(confirm("탈퇴시겠습니까?")){
			    $("#withdrawalFrm").submit();
			    return true;
			} else {
			    return false;
			}
		}
		
	} else {	// 동의 안했을 때
		alert('탈퇴 안내를 확인하고 동의해주세요.');
		return;
	}
});


