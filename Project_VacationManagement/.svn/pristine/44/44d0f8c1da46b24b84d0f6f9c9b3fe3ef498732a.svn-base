$(function() {

	// 세션 확인
	$.ajax({
		url: "/main/session_check",
		type: 'POST',
		dataType: "json",
		success: function(data) {
			if (data.returnCode == "Y") {
				// 올해 사용한 휴가 수 계산 - header.jsp에 연결하기 위해 여기로 옮김
				$.ajax({
					url: "/vacation/count_vacation",
					type: 'POST',
					dataType: "json",
					success: function(data) {
						if (data.result.returnCode == "SUCCESS") {
							$("#countVacation").text(data.cnt);
						} else {
							console.log(data.result.returnDesc);
						}
					},
					error: function(xhr, status) {
						document.write(xhr + " : " + status);
					}
				});
			} else {
				console.log(data.returnDesc);
			}
		},
		error: function(xhr, status) {
			document.write(xhr + " : " + status);
		}
	});


	// 비밀번호 변경 버튼 클릭
	$("#pwUpdateBtn").on("click", function() {

		location.href = "/member/updatePw";
	});

	var updatePwConfirmBtn = $("#updatePwConfirmBtn");
	var currPw = $("#currPw");
	var newPw = $("#newPw");
	var newPwCheck = $("#newPwCheck");
	var infoBox = $(".info-check");
	var messageArr = ["현재 비밀번호를 입력해주세요.", "변경할 비밀번호를 확인해주세요.", "변경할 비밀번호 확인을 해주세요."];

	// 유효성 검사 및 중복 확인에 따른 안내 메시지
	function displayBlock(selectedElement, message) {
		selectedElement.next().text(message).css("display", "block");
	}

	// 안내 메시지 없애기
	function displayNone(selectedElement) {
		selectedElement.next().css("display", "none");
	}

	// class invalid
	function classInvalid(selectedElement) {
		selectedElement.attr("class", "form-control invalid");
	}

	// class valid
	function classValid(selectedElement) {
		selectedElement.attr("class", "form-control valid");
	}

	// 비밀번호 불일치 메시지 표시
	function pwStatus(message) {
		infoBox.text(message).css("display", "block");
	}

	// 변경할 비밀번호 유효성 검사
	newPw.on("keyup", function() {

		newPwCheck.keyup();

		if (newPw.val() == "") {
			classInvalid(newPw);
			displayNone(newPw);
		} else if (newPw.val().length < 8) {

			classInvalid(newPw);
			displayBlock(newPw, "비밀번호는 8자리 이상으로 입력하셔야 합니다.");
			return;
		} else {
			classValid(newPw);
			displayNone(newPw);
		}

	});

	// 변경할 비밀번호 일치 여부 확인
	newPwCheck.on("keyup", function() {

		if (newPwCheck.val() == "") {
			classInvalid(newPwCheck);
			displayNone(newPwCheck);
		} else if (newPw.val() != newPwCheck.val()) {
			classInvalid(newPwCheck);
			displayBlock(newPwCheck, "입력하신 비밀번호와 다릅니다.");
			return;
		} else {
			classValid(newPwCheck);
			displayBlock(newPwCheck, "비밀번호가 일치합니다.");
		}
	});

	// 비밀번호 입력 안하고 변경하기 버튼 눌렀을 때 입력하라는 안내 메시지 뜸. 그 후 비밀번호 입력 시 안내 메시지 없애기
	currPw.on("keypress", function() {
		infoBox.css("display", "none");
		displayNone(currPw);
	});
	//newPw.on("keypress", displayNone);
	//newPwCheck.on("keypress", displayNone);

	// 변경하기 버튼 눌렀을 때 동작
	updatePwConfirmBtn.on("click", updatePwProcess);

	// 엔터키 눌렀을 때 동작
	currPw.on("keypress", enterCheck);
	newPw.on("keypress", enterCheck);
	newPwCheck.on("keypress", enterCheck);

	// 엔터키 눌렀을 때 호출할 함수
	function enterCheck() {
		if (event.keyCode == 13) {
			// 로그인 함수 호출
			updatePwProcess();
		}
	}

	// 변경하기 버튼 눌렀을 때 호출할 함수
	function updatePwProcess() {
		var inputElement = document.querySelectorAll(".form-control");
		for (var i = 0; i < inputElement.length; i++) {
			if ($(inputElement[i]).val() == "" || $(inputElement[i]).attr("class") == "form-control invalid") {
				displayBlock($(inputElement[i]), messageArr[i]);
				return;
			}
		}

		var formData = {
			"currPw": currPw.val(),
			"newPw": newPw.val(),
			"newPwCheck": newPwCheck.val()
		};
		$.ajax({
			url: "/member/update_pw_process",
			type: 'POST',
			data: formData,
			dataType: "json",
			success: function(data) {
				if (data.returnCode == "SUCCESS") {
					location.href = "/member/logout";
				} else {
					pwStatus(data.returnDesc);
				}
			},
			error: function(xhr, status) {
				document.write(xhr + " : " + status);
			}
		});
	}

});