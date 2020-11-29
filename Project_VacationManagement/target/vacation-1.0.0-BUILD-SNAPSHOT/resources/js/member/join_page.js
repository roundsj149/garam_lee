$(function() {

	var employeeNo = $("#employeeNo");
	var emailId = $("#emailId");
	var fullname = $("#fullname");
	var pw = $("#pw");
	var pwCheck = $("#pwCheck");
	var team = $("#team");
	var leaderYn = $("input:radio[name=leaderYn]");
	var adminYn = $("input:radio[name=adminYn]");
	var joinBtn = $("#joinBtn");
	var inputElement= document.querySelectorAll(".form-control");
	var resultArr = ["NO_FAIL", "ID_FAIL", "FULLNAME_FAIL", "PW_FAIL", "PWCHECK_FAIL"];
	var messageArr = ["사번을 확인해주세요.", "아이디를 확인해주세요.", "이름을 확인해주세요.", "비밀번호를 확인해주세요.", "비밀번호 확인을 해주세요."];
	
	// 유효성 검사 및 중복 확인에 따른 안내 메시지
	function displayBlock(selectedElement, message) {
		selectedElement.next().text(message).css("display","block");
	}
	
	// 안내 메시지 없애기
	function displayNone(selectedElement) {
		selectedElement.next().css("display","none");
	}
	
	// class invalid
	function classInvalid(selectedElement) {
		selectedElement.attr("class","form-control invalid");
	}
	
	// class valid
	function classValid(selectedElement) {
		selectedElement.attr("class","form-control valid");
	}

	// 사번 유효성 검사 및 중복 확인
	var employeeNoRegExp = /^[0-9]{6}$/;
	
	employeeNo.on("blur", function() {
		$.ajax({
		    	url: "/member/checkEmployeeNo_process",
		    	type:"post",
		    	data: {"employeeNo": employeeNo.val()},
		    	success:function(result){
		    		if(result.returnCode == "AVAILABLE") {
		    			if (employeeNo.val() == "") {
		    				classInvalid(employeeNo);
							displayNone(employeeNo);
						} else if (employeeNo.val().match(employeeNoRegExp) != null) {
							classValid(employeeNo);
							displayBlock(employeeNo, result.returnDesc);
					 	} else {
					 		classInvalid(employeeNo);
						  	displayBlock(employeeNo, "사번은 6자리 숫자로 입력하셔야 합니다.");
						  	return;
					 	}
		    		} else if(result.returnCode == "DISAVAILABLE") {
		    			classInvalid(employeeNo);
		    			displayBlock(employeeNo, result.returnDesc);
						return;
		    		}
		    	},
		    	error : function(xhr, status) {
                	document.write(xhr + " : " + status);
            	}
		    });
	});
	
	// 아이디 유효성 검사 및 중복 확인
	var idRegExp = /^[a-zA-Z0-9-_\.]{1,20}@dayside.co.kr$/;
	emailId.on("blur", function() {
	
		$.ajax({
			url: "/member/checkEmailId_process",
		    type:"post",
		    data: {"emailId": emailId.val()},
		    success:function(result){
		    	if(result.returnCode == "AVAILABLE") {
		    		if (emailId.val() == "") {
		    			classInvalid(emailId);
						displayNone(emailId);
					} else if (emailId.val().match(idRegExp) != null) {
						classValid(emailId);
						displayBlock(emailId, result.returnDesc);
				 	} else {
				 		classInvalid(emailId);
					  	displayBlock(emailId, "아이디는 데이사이드 이메일 주소를 입력하셔야 합니다.");
					  	return;
				 	}
	    		} else if(result.returnCode == "DISAVAILABLE") {
	    			classInvalid(emailId);
	    			displayBlock(emailId, result.returnDesc);
					return;
	    		}
	    	},
	    	error : function(xhr, status) {
            	document.write(xhr + " : " + status);
            }
	    });		
	});
	
	// 이름 유효성 검사
	var fullnameRegExp = /^[가-힣]{2,5}$/;
	
	fullname.on("keyup", function() {
		if (fullname.val() == "") {
			classInvalid(fullname);
			displayNone(fullname);
		} else if (fullname.val().match(fullnameRegExp) != null) {
			classValid(fullname);
			displayNone(fullname);
		} else {
			classInvalid(fullname);
			displayBlock(fullname, "이름은 2~5자리 한글로 입력하셔야 합니다.");
			return;
		}
	});
	
	// 비밀번호 유효성 검사
	pw.on("keyup", function() {
		
		pwCheck.keyup();
		
		if(pw.val() == "") {
			classInvalid(pw);
			displayNone(pw);
		} else if(pw.val().length < 8) {
			classInvalid(pw);
			displayBlock(pw, "비밀번호는 8자리 이상으로 입력하셔야 합니다.");	
			return;
		} else {
			classValid(pw);
			displayNone(pw);
		}
		
	});
	
	// 비밀번호 일치 여부 확인
	pwCheck.on("keyup", function() {
		
		if(pwCheck.val() == "") {
			classInvalid(pwCheck);
			displayNone(pwCheck);
		} else if(pw.val() != pwCheck.val()) {
			classInvalid(pwCheck);
			displayBlock(pwCheck, "입력하신 비밀번호와 다릅니다.");
			return;
		} else {
			classValid(pwCheck);
			displayBlock(pwCheck, "비밀번호가 일치합니다.");
		}
	});
	
	// 소속팀 선택 확인
	team.on("change", function() {
		if(team.find(":selected").val() != "DEFAULT") {
			displayNone(team);
		}
	});

	// 팀장 여부 선택 확인
	leaderYn.on("click", function() {
		$(".leader-check").css("display","none");
	});
	
	// 관리자 여부 선택 확인
	adminYn.on("click", function() {
		$(".admin-check").css("display","none");
	});
	
	// 가입하기 버튼 눌렀을 때
	joinBtn.on("click", joinProcess);
	
	function joinProcess() {
		
		// 공란 또는 유효성 검사 실패 시 안내메시지
		for(var i=0; i<inputElement.length; i++) {
			if($(inputElement[i]).val() == "" || $(inputElement[i]).attr("class") == "form-control invalid") {
			displayBlock($(inputElement[i]), messageArr[i]);
			return;
			}
		}
		
		if(team.val() == "DEFAULT") {
			displayBlock(team, "소속팀을 선택해주세요.")
		return;
		}
		if(!leaderYn.is(":checked")) {
			$(".leader-check").text("팀장 여부를 선택해주세요").css("display","block");
			return;
		}
		if(!adminYn.is(":checked")) {
			$(".admin-check").text("관리자 여부를 선택해주세요").css("display","block");
			return;
		}
		
		var formData = {"employeeNo": employeeNo.val(),
						"emailId": emailId.val(),
						"fullname": fullname.val(),
						"pw": pw.val(),
						"pwCheck": pwCheck.val(),
						"team": team.find(":selected").val(),
						"leaderYn": $("input:radio[name=leaderYn]:checked").val(),
						"adminYn": $("input:radio[name=adminYn]:checked").val()
						};
		$.ajax({
            url : "/member/join_process",
            type : 'POST', 
            data : JSON.stringify(formData),
            contentType: "application/json",
            success : function(data) {
            	for(var i=0; i<resultArr.length; i++) {
            		if(data.returnCode == resultArr[i]) {
            			displayBlock($(inputElement[i]), data.returnDesc);
            			return;
            		}
            	}
            	if(data.returnCode == "TEAM_FAIL") {
            		displayBlock(team, data.returnDesc);
					return;
            	} else if(data.returnCode == "LEADERYN_FAIL") {
            		$(".leader-check").text(data.returnDesc).css("display","block");
					return;
            	} else if(data.returnCode == "ADMINYN_FAIL") {
            		$(".admin-check").text(data.returnDesc).css("display","block");
					return;
            	} else {
            		location.href = "/member/login";
            	}
            	
            },
			error : function(xhr, status) {
                document.write(xhr + " : " + status);
            }
		});		
	}
});





























