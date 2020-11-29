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
	
	// 사번 유효성 검사 및 중복 확인
	var employeeNoRegExp = /^[0-9]{6}$/;
	
	employeeNo.on("keyup", function() {
		$.ajax({
		    	url: "/vacation/checkEmployeeNo_process",
		    	type:"post",
		    	data: {"employeeNo": employeeNo.val()},
		    	success:function(result){
		    		if(result == "AVAILABLE") {
		    			if (employeeNo.val() == "") {
		    				employeeNo.attr("class","form-control invalid");
							$(".no-check").css("display","none");
						} else if (employeeNo.val().match(employeeNoRegExp) != null) {
							employeeNo.attr("class","form-control valid");
							$(".no-check").text("사용 가능한 사번입니다.").css("display","block");
					 	} else {
					 		employeeNo.attr("class","form-control invalid");
						  	$(".no-check").text("사번은 6자리 숫자로 입력하셔야 합니다.").css("display","block");
						  	return;
					 	}
		    		} else {
		    			employeeNo.attr("class","form-control invalid");
		    			$(".no-check").text("중복된 사번입니다.").css("display","block");
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

	emailId.on("keyup", function() {
	
		$.ajax({
			url: "/vacation/checkEmailId_process",
		    type:"post",
		    data: {"emailId": emailId.val()},
		    success:function(result){
		    	if(result == "AVAILABLE") {
		    		if (emailId.val() == "") {
		    			emailId.attr("class","form-control invalid");
						$(".id-check").css("display","none");
					} else if (emailId.val().match(idRegExp) != null) {
						emailId.attr("class","form-control valid");
						$(".id-check").text("사용 가능한 아이디 입니다.").css("display","block");
				 	} else {
				 		emailId.attr("class","form-control invalid");
					  	$(".id-check").text("아이디는 데이사이드 이메일 주소를 입력하셔야 합니다.").css("display","block");
					  	return;
				 	}
	    		} else {
	    			emailId.attr("class","form-control invalid");
	    			$(".id-check").text("중복된 아이디입니다.").css("display","block");
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
			fullname.attr("class","form-control invalid");
			$(".fullname-check").css("display","none");
		} else if (fullname.val().match(fullnameRegExp) != null) {
			fullname.attr("class","form-control valid");
			$(".fullname-check").css("display","none");
		}
		else {
			fullname.attr("class","form-control invalid");
			$(".fullname-check").text("이름은 2~5자리 한글로 입력하셔야 합니다.").css("display","block");
			return;
		}
	});
	
	// 비밀번호 유효성 검사
	pw.on("keyup", function() {
		
		pwCheck.keyup();
		
		if(pw.val() == "") {
			pw.attr("class","form-control invalid");
			$(".pw-check").css("display","none");	
		} else if(pw.val().length < 8) {
			pw.attr("class","form-control invalid");
			$(".pw-check").text("비밀번호는 8자리 이상으로 입력하셔야 합니다.").css("display","block");	
			return;
		} else {
			pw.attr("class","form-control valid");
			$(".pw-check").css("display","none");		
		}
		
		
	});
	
	// 비밀번호 일치 여부 확인
	pwCheck.on("keyup", function() {
		
		if(pwCheck.val() == "") {
			pwCheck.attr("class","form-control invalid");
			$(".pw-double-check").css("display","none");
		} else if(pw.val() != pwCheck.val()) {
			pwCheck.attr("class","form-control invalid");
			$(".pw-double-check").text("입력하신 비밀번호와 다릅니다.").css("display","block");
			return;
		} else {
			pwCheck.attr("class","form-control valid");
			$(".pw-double-check").text("비밀번호가 일치합니다.").css("display","block");
		}
	});
	
	// 소속팀 선택 확인
	team.on("change", function() {
		
		var selectedTeam = team.find(":selected").val();
		
		if(selectedTeam != "DEFAULT") {
			$(".team-check").css("display","none");
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
	joinBtn.on("click",function() {
		
		if(employeeNo.attr("class") == "form-control invalid" || employeeNo.val()=="") {
			$(".no-check").text("사번을 확인해주세요").css("display","block");
			return;
		}
		if(emailId.attr("class") == "form-control invalid" || emailId.val()=="") {
			$(".id-check").text("아이디를 확인해주세요").css("display","block");
			return;
		}
		if(fullname.attr("class") == "form-control invalid" || fullname.val()=="") {
			$(".fullname-check").text("이름을 확인해주세요").css("display","block");
			return;
		}
		if(pw.attr("class") == "form-control invalid" || pw.val()=="") {
			$(".pw-check").text("비밀번호를 확인해주세요").css("display","block");
			return;
		}
		if(pwCheck.attr("class") == "form-control invalid" || pwCheck.val()=="") {
			$(".pw-double-check").text("비밀번호 확인을 해주세요").css("display","block");
			return;
		}
		if(team.val() == "DEFAULT") {
			$(".team-check").text("소속팀을 선택해주세요").css("display","block");
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
		
		var formData = $("#frm").serialize();
		$.ajax({
            url : "/vacation/join_process",
            type : 'POST', 
            data : formData,
            success : function(data) {
            	if(data == "SUCCESS") {
            		location.href = "/vacation";
            	} else if(data == "NO_FAIL") {
            		$(".no-check").text("사번을 확인해주세요").css("display","block");
            	} else if(data == "ID_FAIL") {
            		$(".id-check").text("아이디를 확인해주세요").css("display","block");
            	} else if(data == "FULLNAME_FAIL") {
            		$(".fullname-check").text("이름을 확인해주세요").css("display","block");
            	} else if(data == "PW_FAIL") {
            		$(".pw-check").text("비밀번호를 확인해주세요").css("display","block");
            	} else if(data == "PWCHECK_FAIL") {
            		$(".pw-double-check").text("비밀번호 확인을 해주세요").css("display","block");
            	} else if(data == "TEAM_FAIL") {
            		$(".team-check").text("소속팀을 선택해주세요").css("display","block");
            	} else if(data == "LEADERYN_FAIL") {
            		$(".leader-check").text("팀장 여부를 선택해주세요").css("display","block");
            	} else if(data == "ADMINYN_FAIL") {
            		$(".admin-check").text("관리자 여부를 선택해주세요").css("display","block");
            	} else {
            		location.href = "/vacation/join";
            	}
            },
			error : function(xhr, status) {
                document.write(xhr + " : " + status);
            }
		});
	});
});





























