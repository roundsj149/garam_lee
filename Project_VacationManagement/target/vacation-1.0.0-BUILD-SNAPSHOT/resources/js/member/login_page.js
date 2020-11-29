$(function() {
	
	var loginBtn = $("#loginBtn");
	var emailId = $("#emailId");
	var pw = $("#pw");
	var infoBox = $(".info-check");
	
	// 아이디, 비밀번호 입력 안하고 로그인 버튼 눌렀을 때 입력하라는 안내 메시지 뜸. 그 후 아이디, 비밀번호 입력 시 안내 메시지 없애기
	emailId.on("keypress", displayNone);
	pw.on("keypress", displayNone);

	// 로그인 버튼 눌렀을 때 동작
	loginBtn.on("click", loginProcess);
	
	// 엔터키 눌렀을 때 동작
	emailId.on("keypress", enterCheck);
	pw.on("keypress", enterCheck);

	// 엔터키 눌렀을 때 호출할 함수
	function enterCheck() {
		if(event.keyCode == 13) {
			// 로그인 함수 호출
			loginProcess();
		}
	}
	
	// 로그인 버튼 눌렀을 때 호출할 함수
	function loginProcess() {
		var inputElement= document.querySelectorAll(".form-control");
		for(var i=0; i<inputElement.length; i++) {
			if($(inputElement[i]).val() == "") {
			displayBlock($(inputElement[i]));
			return;
			}
		}

		var formData = {"emailId": emailId.val(),
						"pw": pw.val()
						};
		$.ajax({
            url : "/member/login_process",
            type : 'POST', 
            data : formData,
            dataType:"json",
            success : function(data) {
            	if(data.returnCode == "JOIN_APPROVAL") {
                	location.href = "/vacation/list";
                } else {
                	joinStatus(data.returnDesc);
                }
            },
			error : function(xhr, status) {
                document.write(xhr + " : " + status);
            }
		});
	}
	
	// 아이디 또는 비밀번호 공란 -> 로그인 버튼 눌렀을 때
	function displayBlock(selectedElement) {
		selectedElement.next().css("display","block");
	}
	
	// 입력 안내메시지 표시 후 입력 시 메시지 없애기
	function displayNone(event) {
		$(event.target.nextElementSibling).css("display","none");
	}
	
	// 가입상태 메시지 표시
	function joinStatus(message) {
		infoBox.text(message).css("display","block");
	}
});