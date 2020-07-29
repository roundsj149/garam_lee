// 아이디, 비밀번호 입력 확인
function login() {
	var frm = $('#frm');
	var idCheck = $('#id_check').val();
	var passwordCheck = $('#password_check').val();
	if (idCheck == null || idCheck == "") {
		alert('아이디를 입력해주세요.');
		return false;
	}

	if (passwordCheck == null || passwordCheck == "") {
		alert('비밀번호를 입력해주세요.');
		return false;
	}

	frm.submit();
}